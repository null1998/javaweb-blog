package com.sduhyd.blog.model;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

public class UserAction{
    private User current_user=null;
    private Connection conn=null;
    public ServletConfig getServletConfig(HttpServletRequest request){
        return (ServletConfig) request.getSession().getServletContext().getAttribute("servletConfig");
    }
    public Connection getConn( ServletConfig sc){
        conn=(Connection) sc.getServletContext().getAttribute("conn");
        return conn;
    }
    public User getCurrentUser(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        current_user =(User)session.getAttribute("current_user");
        return current_user;
    }
    public void login(HttpServletRequest request, HttpServletResponse response)throws IOException {
        ServletConfig servletConfig=getServletConfig(request);
        User current_user=new Utils().login(getConn(servletConfig),request.getParameter("username"),request.getParameter("password"));
        if(current_user.getId() != 0){
            HttpSession session=request.getSession(false);
            session.setAttribute("current_user",current_user);
            response.sendRedirect("/BaseServlet/page/loadMain");

        }else {
            response.sendRedirect("/page/errorPage1.jsp");

        }
    }
    public  void register(HttpServletRequest request, HttpServletResponse response)throws IOException{
        ServletConfig servletConfig=getServletConfig(request);
        Connection conn=(Connection) servletConfig.getServletContext().getAttribute("conn");
        User current_user=new Utils().register(conn,request.getParameter("username"),request.getParameter("password"));
        if(current_user.getId()!= 0) {
            response.sendRedirect("/BaseServlet/page/loadMain");
        }else {
            response.sendRedirect("/page/errorPage1.jsp");
        }
    }
    public void logOut(HttpServletRequest request, HttpServletResponse response)throws IOException{
        HttpSession session = request.getSession(false);
        session.invalidate();
        //这里有个问题，以上两个不起作用？调用后session里的current_user仍然存在
        response.sendRedirect("/BaseServlet/auth/nullUser");
    }
    public void showFavorite(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        ServletConfig servletConfig=getServletConfig(request);
        ArrayList<Essay> arrayList=new UserOP().myFavorite(getConn(servletConfig),getCurrentUser(request).getId());
        if (!arrayList.isEmpty()) {
            Essay[] myfavorite_essays=new SortUtils().reverseEssay(arrayList);
            request.setAttribute("myfavorite_essays",myfavorite_essays);
        }
        request.getRequestDispatcher("/page/myFavorite.jsp").forward(request,response);
    }
    public void deleteFavorite(HttpServletRequest request, HttpServletResponse response)throws IOException{
        ServletConfig servletConfig=getServletConfig(request);
        new UserOP().deleteMyFavorite(getConn(servletConfig),getCurrentUser(request).getId(),Integer.valueOf(request.getParameter("essay_id")));
        response.sendRedirect("/BaseServlet/auth/showFavorite");
    }
    public void createEssay(HttpServletRequest request, HttpServletResponse response)throws UnsupportedEncodingException,IOException{
        ServletConfig servletConfig=getServletConfig(request);
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        User current_user =getCurrentUser(request);
        Integer user_id=current_user.getId();
        String username = current_user.getUsername();
        String title = request.getParameter("create_title");
        String article = request.getParameter("create_article");
        new Utils().createEssay(getConn(servletConfig),user_id,title,article,new Date(),username);
        response.sendRedirect("/BaseServlet/page/loadMain");
    }
    public void showMyEssays(HttpServletRequest request, HttpServletResponse response)throws IOException{
        ServletConfig servletConfig=getServletConfig(request);
        Essay[] essays=new Utils().showEssay(getConn(servletConfig),getCurrentUser(request).getId());
        synchronized (request.getSession(false)){
            request.getSession(false).setAttribute("my_essays", essays);
        }
        response.sendRedirect(request.getContextPath()+"/blog.jsp");
    }
    public  void  updateMyEssay(HttpServletRequest request, HttpServletResponse response)throws UnsupportedEncodingException{
        ServletConfig servletConfig=getServletConfig(request);
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String  id=request.getParameter("id");
        String title=request.getParameter("title");
        String content=request.getParameter("content");
        new Utils().updateEssay(getConn(servletConfig),id,title,content);
    }
}
