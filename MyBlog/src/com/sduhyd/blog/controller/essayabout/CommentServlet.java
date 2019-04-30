package com.sduhyd.blog.controller.essayabout;

import com.sduhyd.blog.controller.EssayPageDataServlet;
import com.sduhyd.blog.model.Utils;
import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "CommentServlet")
public class CommentServlet extends EssayPageDataServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        loadEssayPageData(request,"comment");
        request.setAttribute("current_essay",essay);
        request.setAttribute("sort_comments",sort_comments);
        request.getRequestDispatcher("/essaypage").forward(request,response);
    }
}
