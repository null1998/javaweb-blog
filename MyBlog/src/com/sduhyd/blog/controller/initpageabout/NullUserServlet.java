package com.sduhyd.blog.controller.initpageabout;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.controller.BlogDataServlet;
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

//index.jsp后的第一个servlet。无用户
public class NullUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initNullUser(request,response);
        response.sendRedirect("/InitServlet");
    }

    protected void initNullUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        //设置一个空的user，用户登陆后会覆盖它
        synchronized (request.getSession()){
            HttpSession session = request.getSession(false);
            User null_user=new User();
            null_user.setId(0);
            session.setAttribute("current_user", null_user);
        }
    }

}
