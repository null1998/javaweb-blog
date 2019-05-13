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

public class UserAction {
    public static void login(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException {
        Connection conn=(Connection) sc.getServletContext().getAttribute("conn");
        User current_user=new Utils().login(conn,request.getParameter("username"),request.getParameter("password"));
        if(current_user.getId() != 0){
            HttpSession session=request.getSession(false);
            session.setAttribute("current_user",current_user);
            response.sendRedirect("/BaseServlet/MainPage/getData");
        }else {
            response.sendRedirect("/page/errorPage1.jsp");
        }
    }
    public static void register(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException{
        Connection conn=(Connection) sc.getServletContext().getAttribute("conn");
        User current_user=new Utils().register(conn,request.getParameter("username"),request.getParameter("password"));
        if(current_user.getId()!= 0) {
            response.sendRedirect("/BaseServlet/MainPage/getData");
        }else {
            response.sendRedirect("/page/errorPage1.jsp");
        }
    }
    public static void logOut(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException{
        HttpSession session = request.getSession(false);
        session.invalidate();
        //这里有个问题，以上两个不起作用？调用后session里的current_user仍然存在
        response.sendRedirect("/BaseServlet/NullUser/initNullUser");
    }
    public static void showFavorite(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws ServletException,IOException{
        Connection conn=(Connection) sc.getServletContext().getAttribute("conn");
        HttpSession session=request.getSession(false);
        User current_user =(User)session.getAttribute("current_user");
        ArrayList<Essay> arrayList=new UserOP().myFavorite(conn,current_user.getId());
        if (!arrayList.isEmpty()) {
            Essay[] myfavorite_essays=new SortUtils().reverseEssay(arrayList);
            request.setAttribute("myfavorite_essays",myfavorite_essays);
        }
        request.getRequestDispatcher("/page/myFavorite.jsp").forward(request,response);
    }
    public static void deleteFavorite(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException{
        Connection conn=(Connection) sc.getServletContext().getAttribute("conn");
        HttpSession session=request.getSession(false);
        User current_user =(User)session.getAttribute("current_user");;
        new UserOP().deleteMyFavorite(conn,current_user.getId(),Integer.valueOf(request.getParameter("essay_id")));
        response.sendRedirect("/BaseServlet/UserAction/showFavorite");
    }
    public static void createEssay(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws UnsupportedEncodingException,IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        Connection conn=(Connection) sc.getServletContext().getAttribute("conn");
        HttpSession session=request.getSession(false);
        User current_user =(User)session.getAttribute("current_user");
        Integer user_id=current_user.getId();
        String username = current_user.getUsername();
        String title = request.getParameter("create_title");
        String article = request.getParameter("create_article");
        new Utils().createEssay(conn,user_id,title,article,new Date(),username);
        response.sendRedirect("/BaseServlet/MainPage/getData");
    }
    public static void showMyEssays(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException{
        Connection conn=(Connection) sc.getServletContext().getAttribute("conn");
        HttpSession session=request.getSession(false);
        User current_user =(User)session.getAttribute("current_user");
        Essay[] essays=new Utils().showEssay(conn,current_user.getId());
        synchronized (request.getSession(false)){
            session.setAttribute("my_essays", essays);
        }
        response.sendRedirect(request.getContextPath()+"/blog.jsp");
    }
    public static void  updateMyEssay(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws UnsupportedEncodingException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        Connection conn=(Connection) sc.getServletContext().getAttribute("conn");
        String  id=request.getParameter("id");
        String title=request.getParameter("title");
        String content=request.getParameter("content");
        new Utils().updateEssay(conn,id,title,content);
    }
}
