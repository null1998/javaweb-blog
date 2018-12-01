package com.sduhyd.blog;

import com.sduhyd.blog.User;
import com.sduhyd.blog.Utils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

public class LoginServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession(false);
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        Connection conn=(Connection) getServletContext().getAttribute("conn");
        User user= Utils.login(conn,username,password);
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        if(user != null){
            session.setAttribute("current-user", username);
            session.setAttribute("current-user_id",user.getId());
            System.out.println("用户 "+user.getUsername()+" "+" 登陆成功！");
        }else {
            System.out.println("登录失败！");
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
