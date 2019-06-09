package com.sduhyd.blog.controller;

import com.sduhyd.blog.annotations.AuthCheck;
import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.foundation.RequestMethod;
import com.sduhyd.blog.foundation.annotations.Controller;
import com.sduhyd.blog.foundation.annotations.RequestMapping;
import com.sduhyd.blog.service.UserAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller("auth")
public class AuthController {
	// 这些方法不需要登录就能访问

	@RequestMapping(method = RequestMethod.GET,value="login") // 只响应POST请求
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		User current_user=UserAction.login(username,password);
		HttpSession session=request.getSession(false);
		session.setAttribute("current_user",current_user);
		if(current_user!=null){
			response.sendRedirect("/BaseServlet/page/loadMain");
		}else{
			response.sendRedirect("/page/login.jsp");
		}
	}
	@RequestMapping(method = RequestMethod.POST,value="register") // 只响应POST请求
	public void register(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		if(UserAction.register(username,password)){
			response.sendRedirect("/page/login.jsp");
		}else{
			response.sendRedirect("/page/rigister.jsp");
		}
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
		HttpSession session = request.getSession(false);
		session.invalidate();
		response.sendRedirect("/BaseServlet/page/loadMain");
	}
	@AuthCheck
	@RequestMapping(method = RequestMethod.GET,value="showFavorite")
	public void showFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer current_user_id=Integer.valueOf(request.getParameter("current_user_id"));
		List<Object> myfavorite_essays=UserAction.showFavorite(current_user_id);
		request.setAttribute("myfavorite_essays",myfavorite_essays);
		request.getRequestDispatcher("/page/myFavorite.jsp").forward(request,response);
	}
	@AuthCheck
	@RequestMapping(method = RequestMethod.GET,value="deleteFavorite")
	public void deleteFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer current_user_id=Integer.valueOf(request.getParameter("current_user_id"));
		Integer essay_id=Integer.valueOf(request.getParameter("essay_id"));
		UserAction.deleteFavorite(current_user_id,essay_id);
		response.sendRedirect("/BaseServlet/auth/showFavorite");
	}
	@AuthCheck
	@RequestMapping(method = RequestMethod.POST,value="createEssay")
	public void createEssay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String title = request.getParameter("create_title");
		String article = request.getParameter("create_article");
		HttpSession session=request.getSession(false);
		User current_user=(User)session.getAttribute("current_user");
		UserAction.createEssay(current_user,title,article);
		response.sendRedirect("/BaseServlet/page/loadMain");
	}

}
