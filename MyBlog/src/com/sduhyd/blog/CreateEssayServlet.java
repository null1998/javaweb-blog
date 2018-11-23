package com.sduhyd.blog;

import com.sduhyd.blog.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;


public class CreateEssayServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session= request.getSession(false);
        Integer user_id=(Integer) session.getAttribute("current-user_id");
        String title = request.getParameter("create_title");
        String article = request.getParameter("create_article");
        Date modify_time=new Date();
        Utils.createEssay(user_id,title,article,modify_time);
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           doPost(request,response);
    }
}
