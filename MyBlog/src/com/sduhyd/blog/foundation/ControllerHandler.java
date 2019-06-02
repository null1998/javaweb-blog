package com.sduhyd.blog.foundation;

import com.sduhyd.blog.RequestHandler;
import com.sduhyd.blog.annotations.AuthCheck;
import com.sduhyd.blog.foundation.annotations.Controller;
import com.sduhyd.blog.foundation.annotations.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class ControllerHandler implements RequestHandler {
	private Class<?> originalControllerClass;
	private Method originalControllerMethod;
	private Method proxyMethod;
	private Object controllerInstance;
	private Controller controllerAnnotation;
	private RequestMapping requestHandlerMethodAnnotation;
	/**
	 * 控制器的执行程序（虽然他执行的是控制器代理类）
	 * @param originalControllerClass //比如AuthController
	 * @param originalControllerMethod //比如getUserInfo
	 * @param proxyMethod
	 * @param instance //控制器代理类实例
	 */
	public ControllerHandler(Class<?> originalControllerClass, Method originalControllerMethod, Method proxyMethod, Object instance) {
		this.originalControllerClass = originalControllerClass;
		this.originalControllerMethod = originalControllerMethod;
		this.proxyMethod = proxyMethod;
		this.controllerInstance = instance;
		this.controllerAnnotation = originalControllerClass.getAnnotation(Controller.class);
		this.requestHandlerMethodAnnotation = originalControllerMethod.getAnnotation(RequestMapping.class);
	}
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.proxyMethod.invoke(this.controllerInstance, request, response);
	}
	public boolean canHandleIt(HttpServletRequest request) {
		String requestMethod = request.getMethod();
		if(requestHandlerMethodAnnotation.method() != RequestMethod.ANY) {
			//客户的请求方式，必须与客户所请求的controller方法上的注解相同
			if(!requestHandlerMethodAnnotation.method().name().equals(requestMethod.toUpperCase())) {
				return false;
			}
		}

		return this.requestPath().equals(request.getPathInfo());
	}
	private String requestPath() {
		String c = this.controllerAnnotation.value();//auth
		if(Controller.USE_CLASS_NAME.equals(c)) {//如果忘记注解了就补救下
			c = this.originalControllerClass.getName().replace("Controller", "").toLowerCase();
		}
		String m = this.requestHandlerMethodAnnotation.value();
		if(RequestMapping.USE_METHOD_NAME.equals(m)) {
			m = this.originalControllerMethod.getName();
		}
		return '/' + c + '/' + m;
	}
}
