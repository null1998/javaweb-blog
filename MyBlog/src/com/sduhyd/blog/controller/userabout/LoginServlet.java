package com.sduhyd.blog.controller.userabout;

import com.sduhyd.blog.controller.data.LoadBlogDataServlet;
import com.sduhyd.blog.model.Utils;
import com.sduhyd.blog.bean.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

public class LoginServlet extends LoadBlogDataServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        current_user=new Utils().login(conn,request.getParameter("username"),request.getParameter("password"));
        if(current_user.getId() != 0){
            HttpSession session=request.getSession(false);;
            session.setAttribute("current_user",current_user);
            response.sendRedirect("/InitAndUpdateBlogDataServlet");
        }else {
            response.sendRedirect("/page/errorPage1.jsp");
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request,response);
    }

}
