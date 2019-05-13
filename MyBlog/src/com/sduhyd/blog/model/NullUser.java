package com.sduhyd.blog.model;

import com.sduhyd.blog.bean.User;
import com.sun.deploy.net.HttpResponse;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class NullUser {
    public static void initNullUser(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException {
        //设置一个空的user，用户登陆后会覆盖它
        synchronized (request.getSession()){
            HttpSession session = request.getSession(false);
            User null_user=new User();
            null_user.setId(0);
            session.setAttribute("current_user", null_user);
            response.sendRedirect("/BaseServlet/MainPage/getData");
        }
    }
}
