package com.sduhyd.blog;

import com.sduhyd.blog.Essay;
import com.sduhyd.blog.Utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;


public class ShowEssayServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession(false);
        //session.setAttribute("isShow",1);
        Essay[] essays=Utils.showEssay((Connection) getServletContext().getAttribute("conn"),(Integer) session.getAttribute("current-user_id"));
        ServletContext context = request.getServletContext();
        context.setAttribute("essays", essays);
        response.sendRedirect(request.getContextPath()+"/blog.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
