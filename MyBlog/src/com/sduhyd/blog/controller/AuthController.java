package com.sduhyd.blog.controller;

import com.sduhyd.blog.annotations.AuthCheck;
import com.sduhyd.blog.foundation.RequestMethod;
import com.sduhyd.blog.foundation.annotations.Controller;
import com.sduhyd.blog.foundation.annotations.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("auth")
public class AuthController {
	// 这个方法不需要登录就能访问
	@RequestMapping(method = RequestMethod.POST,value="signUp") // 只响应POST请求
	public void signUp(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.getWriter().print("{\"code\": 1, \"data\": true}");
	}
	@AuthCheck // 必须是已登录的用户才能访问
	@RequestMapping(method = RequestMethod.GET,value="getUserInfo") // 只响应GET请求
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.getWriter().print("{\"code\": 1, \"data\": {}}");
	}
	@RequestMapping(method = RequestMethod.GET,value="getSystemVersion")
	public void getSystemVersion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.getWriter().print("{\"code\": 1, \"data\": \"0.0.1\"}");
	}
}
