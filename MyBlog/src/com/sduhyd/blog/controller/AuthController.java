package com.sduhyd.blog.controller;

import com.sduhyd.blog.annotations.AuthCheck;
import com.sduhyd.blog.foundation.RequestMethod;
import com.sduhyd.blog.foundation.annotations.Controller;
import com.sduhyd.blog.foundation.annotations.RequestMapping;
import com.sduhyd.blog.model.NullUser;
import com.sduhyd.blog.model.UserAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller("auth")
public class AuthController {
	// 这些方法不需要登录就能访问
	@RequestMapping(method = RequestMethod.ANY,value="nullUser")
	public void nullUser(HttpServletRequest request, HttpServletResponse response)throws IOException {
		NullUser.initNullUser(request,response);
	}

	@RequestMapping(method = RequestMethod.POST,value="register") // 只响应POST请求
	public void register(HttpServletRequest request, HttpServletResponse response) throws Exception{
		new UserAction().register(request,response);
	}
	@RequestMapping(method = RequestMethod.GET,value="login") // 只响应POST请求
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception{
		new UserAction().login(request,response);
	}

	@AuthCheck // 必须是已登录的用户才能访问
	@RequestMapping(method = RequestMethod.GET,value="getUserInfo") // 只响应GET请求
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.getWriter().print("{\"code\": 1, \"data\": {}}");
	}
	@AuthCheck
	@RequestMapping(method = RequestMethod.GET,value="getSystemVersion")
	public void getSystemVersion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.getWriter().print("{\"code\": 1, \"data\": \"0.0.1\"}");
	}
	@AuthCheck
	@RequestMapping(method = RequestMethod.GET,value="logOut")
	public void logOut(HttpServletRequest request, HttpServletResponse response) throws Exception{
		new UserAction().logOut(request,response);
	}
	@AuthCheck
	@RequestMapping(method = RequestMethod.GET,value="showFavorite")
	public void showFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception{
		new UserAction().showFavorite(request,response);
	}
	@AuthCheck
	@RequestMapping(method = RequestMethod.GET,value="deleteFavorite")
	public void deleteFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception{
		new UserAction().deleteFavorite(request,response);
	}
	@AuthCheck
	@RequestMapping(method = RequestMethod.POST,value="createEssay")
	public void createEssay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		new UserAction().createEssay(request,response);
	}
}
