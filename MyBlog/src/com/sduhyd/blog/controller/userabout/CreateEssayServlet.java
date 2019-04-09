package com.sduhyd.blog.controller.userabout;

import com.sduhyd.blog.controller.BlogDataServlet;
import com.sduhyd.blog.model.Utils;
import com.sduhyd.blog.bean.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;


public class CreateEssayServlet extends BlogDataServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        loadContextData(request,response);
        loadSessionData(request,response);
        Integer user_id=current_user.getId();
        String username = current_user.getUsername();
        String title = request.getParameter("create_title");
        String article = request.getParameter("create_article");
        Date modify_time=new Date();
        new Utils().createEssay(conn,user_id,title,article,modify_time,username);
        response.sendRedirect("/InitServlet");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
    @Override
    public void destroy() {
        super.destroy();
    }
}
