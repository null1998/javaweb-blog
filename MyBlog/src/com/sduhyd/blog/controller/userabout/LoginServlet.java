package com.sduhyd.blog.controller.userabout;

import com.sduhyd.blog.model.Utils;
import com.sduhyd.blog.bean.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
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
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        Connection conn=(Connection) getServletContext().getAttribute("conn");
        User user=new Utils().login(conn,username,password);
        Cookie cookie=new Cookie("username",username);
        cookie.setMaxAge(60*60*24);
        if(user != null){//
            response.addCookie(cookie);
            synchronized (request.getSession(false)){
                HttpSession session = request.getSession(false);
                session.setAttribute("current_user", user);
            }
            response.sendRedirect("/InitAndUpdateBlogDataServlet");
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
