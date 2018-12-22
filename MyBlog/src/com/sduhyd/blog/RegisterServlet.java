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
            System.out.println("用户 "+user.getUsername()+" 注册成功！");
            response.sendRedirect("/AllEssayServlet");
        }else {
            response.sendRedirect("/page/errorPage1.jsp");
            System.out.println("注册失败");
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
