package com.sduhyd.blog.controller.essayabout;

import com.sduhyd.blog.bean.Comment;
import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.controller.EssayPageDataServlet;
import com.sduhyd.blog.model.EssayOP;
import com.sduhyd.blog.model.SortUtils;
import com.sduhyd.blog.model.Utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "FavoriteServlet")
public class FavoriteServlet extends EssayPageDataServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadEssayPageData(request,"favorite");
        request.setAttribute("current_essay",essay);
        request.setAttribute("current_comments",sort_comments);
        request.getRequestDispatcher("/page/singleBlog.jsp").forward(request,response);
    }
}
