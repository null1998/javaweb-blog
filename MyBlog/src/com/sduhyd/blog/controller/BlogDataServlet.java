package com.sduhyd.blog.controller;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.model.SortUtils;
import com.sduhyd.blog.model.Utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

//帮助载入context数据的类和整个项目需要的session数据,同时作为EssayPageDataServlet和其它一些Servlet的基类
@WebServlet(name = "BlogDataServlet")
public class BlogDataServlet extends HttpServlet {
    protected Connection conn;
    protected Essay[]essays=null;
    protected Essay[]top_essays=null;
    protected User current_user;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void loadContextData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        conn=(Connection) getServletContext().getAttribute("conn");
        synchronized (getServletContext()){
            ServletContext context=getServletContext();
            essays=(Essay[]) context.getAttribute("all_essays");
            top_essays=(Essay[]) context.getAttribute("top_essays");
        }
    }
    protected void loadSessionData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session=request.getSession(false);;
        current_user=(User)session.getAttribute("current_user");
    }
}
