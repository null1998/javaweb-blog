package com.sduhyd.blog.handlers;

import com.sduhyd.blog.annotations.AuthCheck;
import com.sduhyd.blog.foundation.ProxyHandler;
import com.sduhyd.blog.foundation.ProxyPoint;

import javax.servlet.http.HttpServletResponse;

public class AuthCheckHandler extends ProxyHandler.Abstract<AuthCheck> {
	private AuthCheck annot;
	@Override
	public Object handle(Object[] arguments, ProxyPoint point) throws Exception{
		if(true) { //annot.value()!=current_user_id
			// 权限校验通过调用原方法
			System.out.println("权限校验通过，" + point.getTargetMethod() + "方法将会被调用到");
			return point.proceed(arguments);
		}
		System.out.println("权限校验未通过，" + point.getTargetMethod() + "方法将不会被调用到");
//		final HttpServletRequest request = (HttpServletRequest)arguments[0];
		final HttpServletResponse response = (HttpServletResponse)arguments[1];
		// TODO: 校验不通过
		response.setStatus(401); // Unauthorized error
		response.getWriter().print("{\"code\": 401, \"message\": \"请登录！\"}");
		return null;
	}

	@Override
	public void setProxyAnnot(AuthCheck annot) {
		this.annot = annot;
	}
}
