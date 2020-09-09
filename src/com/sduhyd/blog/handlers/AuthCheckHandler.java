package com.sduhyd.blog.handlers;

import com.sduhyd.blog.annotations.AuthCheck;
import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.foundation.ProxyHandler;
import com.sduhyd.blog.foundation.ProxyPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 权限检查，只有登录的用户可以继续执行
 */
public class AuthCheckHandler extends ProxyHandler.AbstractProxyHandler<AuthCheck> {
	private AuthCheck annot;
	@Override
	public Object handle(Object[] arguments, ProxyPoint point) throws Exception{
		HttpServletRequest request = (HttpServletRequest)arguments[0];

		HttpSession session = request.getSession(false);
		User currentUser = (User)session.getAttribute("current_user");
		if(currentUser != null) {
			// 权限校验通过调用原方法
			System.out.println("已登录，权限校验通过，" + point.getTargetMethod() + "方法将会被调用到");
			return point.proceed(arguments);
		}
		System.out.println("未登录，权限校验未通过，重定向至登录界面");
//		final HttpServletRequest request = (HttpServletRequest)arguments[0];
		final HttpServletResponse response = (HttpServletResponse)arguments[1];

		response.setStatus(401); // Unauthorized error
		response.sendRedirect("/page/login.jsp");

		return null;
	}

	@Override
	public void setProxyAnnot(AuthCheck annot) {
		this.annot = annot;
	}
}
