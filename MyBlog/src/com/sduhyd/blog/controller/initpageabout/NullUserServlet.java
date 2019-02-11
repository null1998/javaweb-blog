package com.sduhyd.blog.controller.initpageabout;

import com.sduhyd.blog.bean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class NullUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置一个空的user，用户登陆后会覆盖它
        synchronized (request.getSession()){
            HttpSession session = request.getSession(false);
            User null_user=new User();
            null_user.setId(0);
            session.setAttribute("current_user", null_user);
        }
        response.sendRedirect("/mainpage");
    }
}
