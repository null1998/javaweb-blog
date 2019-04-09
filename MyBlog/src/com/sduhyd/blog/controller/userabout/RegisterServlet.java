package com.sduhyd.blog.controller.userabout;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import com.sduhyd.blog.model.Utils;
import com.sduhyd.blog.bean.User;

public class RegisterServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        Connection conn=(Connection) getServletContext().getAttribute("conn");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        User user=new Utils().register(conn,username,password);
        if(user != null) {
            response.sendRedirect("/InitServlet");
        }else {
            response.sendRedirect("/page/errorPage1.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doGet(request,response);
    }
    @Override
    public void destroy() {
        super.destroy();
    }
}
