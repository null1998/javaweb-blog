package com.sduhyd.blog.controller;

import com.sduhyd.blog.annotations.AuthCheck;
import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.foundation.RequestMethod;
import com.sduhyd.blog.foundation.annotations.Controller;
import com.sduhyd.blog.foundation.annotations.RequestMapping;
import com.sduhyd.blog.service.UserAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
			response.sendRedirect("/page/register.jsp");
		}
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
		HttpSession session=request.getSession(false);
		User current_user=(User)session.getAttribute("current_user");
		List<Object> myfavorite_essays = UserAction.showFavorite(current_user.getId());
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
	/**
	 * 进入写文章界面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@AuthCheck
	@RequestMapping(method = RequestMethod.GET,value="loadWriteEssayPage")
	public void loadWriteEssayPage(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
		request.getRequestDispatcher("/page/writeEssay.jsp").forward(request,response);
	}
	@AuthCheck
	@RequestMapping(method = RequestMethod.POST,value="writeEssay")
	public void writeEssay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String title = request.getParameter("create_title");
		String article = request.getParameter("create_article");
		HttpSession session=request.getSession(false);
		User current_user=(User)session.getAttribute("current_user");
		UserAction.writeEssay(current_user,title,article);
		response.sendRedirect("/BaseServlet/page/loadMain");
	}
	@AuthCheck
	@RequestMapping(method = RequestMethod.GET,value="starEssay")
	public void starEssay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession(false);
		User current_user=(User)session.getAttribute("current_user");
		Integer essay_id=Integer.valueOf(request.getParameter("essay_id"));
		UserAction.starEssay(essay_id, current_user.getId());
		request.getRequestDispatcher("/BaseServlet/page/loadEssay").forward(request,response);
	}
	@AuthCheck
	@RequestMapping(method = RequestMethod.GET,value="dissEssay")
	public void dissEssay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession(false);
		User current_user=(User)session.getAttribute("current_user");
		Integer essay_id=Integer.valueOf(request.getParameter("essay_id"));
		UserAction.dissEssay(essay_id, current_user.getId());
		request.getRequestDispatcher("/BaseServlet/page/loadEssay").forward(request,response);
	}
	@AuthCheck
	@RequestMapping(method = RequestMethod.GET,value="favoriteEssay")
	public void favoriteEssay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession(false);
		User current_user=(User)session.getAttribute("current_user");
		Integer essay_id=Integer.valueOf(request.getParameter("essay_id"));
		UserAction.favoriteEssay(essay_id, current_user.getId());
		request.getRequestDispatcher("/BaseServlet/page/loadEssay").forward(request,response);
	}
	@AuthCheck
	@RequestMapping(method = RequestMethod.POST,value="commentEssay")
	public void commentEssay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		HttpSession session=request.getSession(false);
		User current_user=(User)session.getAttribute("current_user");
		Integer essay_id=Integer.valueOf(request.getParameter("essay_id"));
		String comment = request.getParameter("comment");
		UserAction.commentEssay(essay_id,comment,current_user);
		request.getRequestDispatcher("/BaseServlet/page/loadEssay").forward(request,response);
	}
	@AuthCheck
	@RequestMapping(method = RequestMethod.GET,value="starComment")
	public void starComment(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession(false);
		User current_user=(User)session.getAttribute("current_user");
		Integer comment_id=Integer.valueOf(request.getParameter("comment_id"));
		String comment = request.getParameter("comment");
		UserAction.starComment(comment_id,current_user.getId());
		request.getRequestDispatcher("/BaseServlet/page/loadEssay").forward(request,response);
	}
	@AuthCheck
	@RequestMapping(method = RequestMethod.GET,value="dissComment")
	public void dissComment(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession(false);
		User current_user=(User)session.getAttribute("current_user");
		Integer comment_id=Integer.valueOf(request.getParameter("comment_id"));
		String comment = request.getParameter("comment");
		UserAction.dissComment(comment_id,current_user.getId());
		request.getRequestDispatcher("/BaseServlet/page/loadEssay").forward(request,response);
	}

}
