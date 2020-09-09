package com.sduhyd.blog.foundation;

import com.sduhyd.blog.RequestHandler;
import com.sduhyd.blog.annotations.AuthCheck;
import com.sduhyd.blog.foundation.annotations.Controller;
import com.sduhyd.blog.foundation.annotations.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 执行器
 */
public class ControllerHandler implements RequestHandler {
	private Class<?> originalControllerClass;
	private Method originalControllerMethod;
	private Method proxyMethod;
	private Object proxyControllerInstance;
	private Controller controllerAnnotation;
	private RequestMapping requestHandlerMethodAnnotation;
	/**
	 *
	 * @param originalControllerClass //原Class对象
	 * @param originalControllerMethod //原方法
	 * @param proxyMethod//代理方法
	 * @param proxyInstance //代理类实例
	 */
	public ControllerHandler(Class<?> originalControllerClass, Method originalControllerMethod, Method proxyMethod, Object proxyInstance) {
		this.originalControllerClass = originalControllerClass;
		this.originalControllerMethod = originalControllerMethod;
		this.proxyMethod = proxyMethod;
		this.proxyControllerInstance = proxyInstance;

		this.controllerAnnotation = originalControllerClass.getAnnotation(Controller.class);
		this.requestHandlerMethodAnnotation = originalControllerMethod.getAnnotation(RequestMapping.class);
	}
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.proxyMethod.invoke(this.proxyControllerInstance, request, response);
	}
	public boolean canHandleIt(HttpServletRequest request) {
		String requestMethod = request.getMethod();

		if(requestHandlerMethodAnnotation.method() != RequestMethod.ANY) {
			//客户的请求方式，必须与客户所请求的controller方法上的RequestMapping注解相同
			if(!requestHandlerMethodAnnotation.method().name().equals(requestMethod.toUpperCase())) {
				return false;
			}
		}
		//前端的路径与注解上的值相符合
		String requestPath = request.getPathInfo();

		return this.getRequestPath().equals(requestPath);
	}
	private String getRequestPath() {
		String c = this.controllerAnnotation.value();
		//针对注释忘记赋值的情况
		if(Controller.USE_CLASS_NAME.equals(c)) {
			c = this.originalControllerClass.getName().replace("Controller", "").toLowerCase();
		}
		String m = this.requestHandlerMethodAnnotation.value();
		if(RequestMapping.USE_METHOD_NAME.equals(m)) {
			m = this.originalControllerMethod.getName();
		}
		return '/' + c + '/' + m;
	}
}
