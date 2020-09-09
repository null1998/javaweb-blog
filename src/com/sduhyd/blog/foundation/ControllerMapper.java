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
     * 方法筛选器(javassist.util.proxy.MethodFilter)，自定义筛选逻辑
     */
    private static final MethodFilter REQUEST_HANDLER_METHOD_FILTER = new MethodFilter() {
        @Override
        public boolean isHandled(Method method) {
            RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
            return methodAnnotation != null;
        }
    };
    //控制器所属包名
    private String controllerPackageName;

    //ControllerHandler：自定义的一个数据结构，包含原方法，代理方法等字段
    private Collection<ControllerHandler> controllerHandlerCollection;
    public ControllerMapper(String controllerPackageName) {
        this.controllerPackageName = controllerPackageName;
        this.controllerHandlerCollection = new LinkedList<>();
    }
    public void init() throws Exception{
        CodeSource source = ControllerMapper.class.getProtectionDomain().getCodeSource();
        String classesPath = source.getLocation().getPath();
        ClassScanner scanner = new ClassScanner(classesPath);

        //加入自定义的类筛选器，自定义筛选逻辑
        scanner.addFilter(new ClassFilter() {
            @Override
            public boolean accept(Class<?> clazz) {
                Controller controllerAnnotationInstance = clazz.getAnnotation(Controller.class);
                return controllerAnnotationInstance != null;
            }
        });

        Collection<Class<?>> controllerClassCollection = scanner.scan(controllerPackageName);

        this.prepareHandlers(controllerClassCollection);
    }
    /**
     *
     *
     * @param controllerClassCollection
     * @throws Exception
     */
    private void prepareHandlers(Collection<Class<?>> controllerClassCollection) throws Exception {

        for (final Class<?> controllerClass: controllerClassCollection) {

            Constructor<?> constructor = controllerClass.getConstructor();
            final Object originControllerInstance = constructor.newInstance();

            //Map：controller所有请求方法和方法对应的所有的代理类的实例
            final Map<Method, Collection<ProxyHandler>> proxyHandlersMap = this.resolveControllerProxyHandlers(controllerClass);

            //代理工厂(javassist.util.proxy.ProxyFactory)
            ProxyFactory factory = new ProxyFactory();
            //指定要代理的类
            factory.setSuperclass(controllerClass);
            //筛选代理方法(javassist.util.proxy.MethodFilter)
            factory.setFilter(REQUEST_HANDLER_METHOD_FILTER);
            Object proxiedControllerInstance = factory.create(new Class[0], new Object[0], new MethodHandler() {
                /**
                 *javassist.util.proxy.MethodHandler 自定义代理逻辑
                 * @param self 原对象
                 * @param thisMethod 原方法
                 * @param proceed 修改后的方法
                 * @param args 方法参数
                 * @return
                 * @throws Throwable
                 */
                @Override
                public Object invoke(Object self, final Method thisMethod, Method proceed, Object[] args) throws Throwable {
                    Collection<ProxyHandler> handlers = new LinkedList<>(proxyHandlersMap.get(thisMethod));

                    //加上最后一个代理实例，用来执行原方法
                    handlers.add(new ProxyHandler.AbstractProxyHandler<Annotation>() {
                        @Override
                        public Object handle(Object[] arguments, ProxyPoint point) throws Exception {
                            //利用反射执行原方法逻辑即可
                            return thisMethod.invoke(originControllerInstance, arguments);
                        }
                    });

                    //这里开始正式执行代理链上的所有逻辑
                    ProxyPoint point = new ProxyPoint(thisMethod, controllerClass, handlers);
                    return point.proceed(args);
                }
            });

            for (Method method: proxyHandlersMap.keySet()) {
                Method proxyMethod = proxiedControllerInstance.getClass().getMethod(method.getName(), method.getParameterTypes());
                this.controllerHandlerCollection.add(new ControllerHandler(controllerClass, method, proxyMethod, proxiedControllerInstance));
            }
        }
    }

    /**
     *key 单个controller的请求方法
     * value 请求方法所有的代理类的实例
     * @param controllerClass
     * @return
     * @throws Exception
     */
    private Map<Method, Collection<ProxyHandler>> resolveControllerProxyHandlers(Class<?> controllerClass) throws Exception{

        Map<Method,Collection<ProxyHandler>> proxyHandlersMap = new ProxyHandlerMap();
        Method[] methods = controllerClass.getMethods();

        for(Method method: methods) {

            //该方法要满足RequestMapping注解，说明该方法是请求方法
            if (method.getAnnotation(RequestMapping.class) != null) {
                Annotation[] annotations = method.getDeclaredAnnotations();

                Collection<ProxyHandler> handlers = proxyHandlersMap.get(method);

                for(Annotation annot: annotations) {

                    //该注解要被元注解ProxyAnnotation注释，说明该注解声明了某种代理逻辑
                    ProxyAnnotation proxyAnnotation = annot.annotationType().getAnnotation(ProxyAnnotation.class);
                    if(proxyAnnotation != null) {
                        //创建代理实例
                        ProxyHandler handlerInstance = proxyAnnotation.value().newInstance();
                        handlerInstance.setProxyAnnot(annot);
                        handlers.add(handlerInstance);
                    }
                }
            }
        }
        return proxyHandlersMap;
    }
    public RequestHandler getHandler(HttpServletRequest request) {
        for(ControllerHandler handler : this.controllerHandlerCollection) {
            if(handler.canHandleIt(request)) {
                return handler;
            }
        }

        return new NotFoundRequestHandler();
    }
    private static class ProxyHandlerMap extends HashMap<Method, Collection<ProxyHandler>> implements Map<Method, Collection<ProxyHandler>> {
        //ProxyHandlerMap的父类已经有了map的所有方法，所以这里不用实现所有接口方法。
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
