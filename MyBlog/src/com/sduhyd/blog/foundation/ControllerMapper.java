package com.sduhyd.blog.foundation;

import com.sduhyd.blog.RequestHandler;
import com.sduhyd.blog.foundation.annotations.Controller;
import com.sduhyd.blog.foundation.annotations.ProxyAnnotation;
import com.sduhyd.blog.foundation.annotations.RequestMapping;
import com.sduhyd.blog.handlers.NotFoundRequestHandler;
import javassist.*;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.CodeSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ControllerMapper {
    /**
     * 只要方法上加了注解，标明POST或GET，这个方法就可以进行处理。
     */
    private static final MethodFilter REQUEST_HANDLER_METHOD_FILTER = new MethodFilter() {
        @Override
        public boolean isHandled(Method method) {
            RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
            return methodAnnotation != null;
        }
    };

    private String controllerPackage;
    //
    private Collection<ControllerHandler> controllerHandlers;
    public ControllerMapper(String controllerPackage) {
        this.controllerPackage = controllerPackage;
        this.controllerHandlers = new LinkedList<>();
    }
    public void init() throws Exception{
        CodeSource source = ControllerMapper.class.getProtectionDomain().getCodeSource();
        ClassScanner scanner = new ClassScanner(source.getLocation().getPath());
        //"/C:/Users/test/IdeaProjects/MyBlog/out/artifacts/MyBlog_war_exploded/WEB-INF/classes/"
        System.out.println("sourcePath "+source.getLocation().getPath());
        //对将要扫描的类进行筛选（目前仅要求有Controller注解）
        scanner.addFilter(new ClassFilter() {
            @Override
            public boolean accept(Class<?> clazz) {
                Controller controllerAnnotationInstance = clazz.getAnnotation(Controller.class);
                return controllerAnnotationInstance != null;
            }
        });
        Collection<Class<?>> controllerClasses = scanner.scan(controllerPackage);
        this.prepareHandlers(controllerClasses);
    }
    /**
     * 对每一个controller，实例化，在resolveControllerProxyHandlers中处理后返回键值对
     * 如<getUserInfo,只有AuthCheckHandler的集合>
     * 实例化一个MethodHandler的匿名内部类，invoke方法里执行如下：
     * 把map里的集合取出，往其中再加入一个元素，执行proceed方法第一次执行了AuthCheckHandler的handle方法，
     * 因为有@Auth所以验证通过，再次执行proceed方法，因为此时interator已经翻过第二个元素，所以执行了ProxyHandler.Abstract的handle方法
     * 类似getUserInfo.invoke(AuthCheckHandler,arguments);
     *
     * 生成代理controller实例
     *
     * @param controllerClasses
     * @throws Exception
     */
    private void prepareHandlers(Collection<Class<?>> controllerClasses) throws Exception {
        for (final Class<?> controllerClass: controllerClasses) {
            Constructor<?> constructor = controllerClass.getConstructor();
            final Object originControllerInstance = constructor.newInstance();
            ProxyFactory factory = new ProxyFactory();
            factory.setSuperclass(controllerClass);
            factory.setFilter(REQUEST_HANDLER_METHOD_FILTER);
            final Map<Method, Collection<ProxyHandler>> proxyHandlersMap = this.resolveControllerProxyHandlers(controllerClass);

            Object proxiedControllerInstance = factory.create(new Class[0], new Object[0], new MethodHandler() {
                /**
                 *
                 * @param self 控制器代理类的实例
                 * @param thisMethod 原方法
                 * @param proceed
                 * @param args 要执行方法的参数
                 * @return
                 * @throws Throwable
                 */
                @Override
                public Object invoke(Object self, final Method thisMethod, Method proceed, Object[] args) throws Throwable {
                    Collection<ProxyHandler> handlers = new LinkedList<>(proxyHandlersMap.get(thisMethod));

                    handlers.add(new ProxyHandler.Abstract<Annotation>() {
                        @Override
                        public Object handle(Object[] arguments, ProxyPoint point) throws Exception {
                            return thisMethod.invoke(originControllerInstance, arguments);
                        }
                    });
                    ProxyPoint point = new ProxyPoint(thisMethod, controllerClass, handlers);
                    return point.proceed(args);
                }
            });
            //暂时只有一个getUserInfo
            for (Method method: proxyHandlersMap.keySet()) {
                Method proxyMethod = proxiedControllerInstance.getClass().getMethod(method.getName(), method.getParameterTypes());
                this.controllerHandlers.add(new ControllerHandler(controllerClass, method, proxyMethod, proxiedControllerInstance));
            }
        }
    }

    /**
     * 传入一个controller，列出所有方法，把其中用于处理请求的，且方法上有一种注解，该注解被@ProxyAnnotation注解
     * 这些方法和@ProxyAnnotation的值（校验逻辑，如AuthCheckHandler的实例）构成一个键值对
     * 放入Map<Method, Collection<ProxyHandler>>
     * @param controllerClass
     * @return 目前handlers里只有一个元素，该元素为<getUserInfo,暂时只有AuthCheckHandler（该实例里有对应的注解@Auth）的集合>
     * @throws Exception
     */
    private Map<Method, Collection<ProxyHandler>> resolveControllerProxyHandlers(Class<?> controllerClass) throws Exception{
        Map<Method,Collection<ProxyHandler>> handlersMap = new ProxyHandlerMap();
        Method[] methods = controllerClass.getMethods();
        for(Method method: methods) {
            //只有能够处理请求的方法才继续执行
            if (method.getAnnotation(RequestMapping.class) != null) {
                Annotation[] annotations = method.getDeclaredAnnotations();
                //用这个method在handlersMap里设置了一个<Method,Collection<ProxyHandler>>,然后再根据method取出来，然而Collection<ProxyHandler>为空。
                Collection<ProxyHandler> handlers = handlersMap.get(method);
                /**
                 * 把method上的注解取出来，看看有没有被@ProxyAnnotation注解了，目前只有@Auth满足条件
                 * 将校验逻辑类，如AuthCheckHandler实例化，并把满足条件的注解，如@Auth赋给该实例的成员
                 * 最后把此实例放入集合
                 */
                for(Annotation annot: annotations) {
                    //annot.annotationType()即AuthCheck.Class
                    ProxyAnnotation proxyAnnotation = annot.annotationType().getAnnotation(ProxyAnnotation.class);
                    if(proxyAnnotation != null) {
                        ProxyHandler handlerInstance = proxyAnnotation.value().newInstance();
                        handlerInstance.setProxyAnnot(annot);
                        handlers.add(handlerInstance);
                    }
                }
            }
        }
        return handlersMap;
    }
    public RequestHandler getHandler(HttpServletRequest request) {
        for(ControllerHandler handler : this.controllerHandlers) {
            if(handler.canHandleIt(request)) {
                System.out.println("Handler found for request: " + request.getPathInfo());
                return handler;
            }
        }
        System.out.println("Not handler found for request: "+ request.getPathInfo());
        return new NotFoundRequestHandler();
    }
    private static class ProxyHandlerMap extends HashMap<Method, Collection<ProxyHandler>> implements Map<Method, Collection<ProxyHandler>> {
        //ProxyHandlerMap的父类已经有了map的所有方法，所以这里不用实现接口。
        @Override
        public Collection<ProxyHandler> get(Object key) {
            if(key instanceof Method) {
                return this.get((Method)key);
            } else {
                return null;
            }
        }

        public Collection<ProxyHandler> get(Method key) {
            if(!this.containsKey(key)) {
                this.put(key, new LinkedList<ProxyHandler>());
            }
            return super.get(key);
        }

    }
}
