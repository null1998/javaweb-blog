package com.sduhyd.blog.controller.data;

import com.sduhyd.blog.bean.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *index.jsp后的第一个Servlet。这时处于没有用户登录的状态
 * 为了后续Servlet正常使用current_user，这里创建一个虚拟的用户
 * 用来模拟无用户登录的状态
 * */

public class NullUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initNullUser(request);
        response.sendRedirect("/InitAndUpdateBlogDataServlet");
    }

    protected void initNullUser(HttpServletRequest request){
        //设置一个空的user，用户登陆后会覆盖它
        synchronized (request.getSession()){
            HttpSession session = request.getSession(false);
            User null_user=new User();
            null_user.setId(0);
            session.setAttribute("current_user", null_user);
        }
    }

}
