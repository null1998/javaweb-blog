package com.sduhyd.blog.controller.userabout;

import com.sduhyd.blog.controller.data.LoadBlogDataServlet;
import com.sduhyd.blog.model.UserOP;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteMyFavoriteServlet")
public class DeleteMyFavoriteServlet extends LoadBlogDataServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
             doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        new UserOP().deleteMyFavorite(conn,current_user.getId(),Integer.valueOf(request.getParameter("essay_id")));
        response.sendRedirect("/MyFavoriteServlet");
    }
}
