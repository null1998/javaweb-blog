package com.sduhyd.blog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import com.sduhyd.blog.User;
import com.sduhyd.blog.Utils;

public class RegisterServlet extends HttpServlet {
    private Connection conn=null;
    private Utils utils;
    @Override
    public void init() throws ServletException {
        super.init();
        utils =new Utils();
        conn=utils.connection();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String result = "";
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        User user=utils.register(conn,username,password);
        response.sendRedirect(request.getContextPath()+"/index.jsp");
        if(user != null) {
            result="register";
            System.out.println(user.getUsername()+" "+result);
        }else {
            System.out.println("注册失败");
        }
    }
    @Override
    public void destroy() {
        super.destroy();
        utils.releaseConnection(conn);
    }
}
