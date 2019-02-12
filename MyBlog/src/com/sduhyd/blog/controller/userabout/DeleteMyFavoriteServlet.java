package com.sduhyd.blog.controller.userabout;

import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.model.UserOP;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "DeleteMyFavoriteServlet")
public class DeleteMyFavoriteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
             doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        ServletContext context = request.getServletContext();
        Connection conn=(Connection) context.getAttribute("conn");
        new UserOP().deleteMyFavorite(conn,Integer.valueOf(request.getParameter("user_id")),Integer.valueOf(request.getParameter("essay_id")));
        response.sendRedirect("/MyFavoriteServlet");
    }
}
