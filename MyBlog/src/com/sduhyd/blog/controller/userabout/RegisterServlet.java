package com.sduhyd.blog.controller.userabout;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import com.sduhyd.blog.controller.data.LoadBlogDataServlet;
import com.sduhyd.blog.model.Utils;
import com.sduhyd.blog.bean.User;

public class RegisterServlet extends LoadBlogDataServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        current_user=new Utils().register(conn,request.getParameter("username"),request.getParameter("password"));
        if(current_user.getId()!= 0) {
            response.sendRedirect("/InitAndUpdateBlogDataServlet");
        }else {
            response.sendRedirect("/page/errorPage1.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doGet(request,response);
    }
}
