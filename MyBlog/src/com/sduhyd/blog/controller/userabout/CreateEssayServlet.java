package com.sduhyd.blog.controller.userabout;

import com.sduhyd.blog.controller.data.LoadBlogDataServlet;
import com.sduhyd.blog.model.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


public class CreateEssayServlet extends LoadBlogDataServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        Integer user_id=current_user.getId();
        String username = current_user.getUsername();
        String title = request.getParameter("create_title");
        String article = request.getParameter("create_article");
        Date modify_time=new Date();
        new Utils().createEssay(conn,user_id,title,article,modify_time,username);
        response.sendRedirect("/InitAndUpdateBlogDataServlet");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
