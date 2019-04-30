package com.sduhyd.blog.controller.userabout;

import com.sduhyd.blog.model.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

//暂时没用
public class UpdateEssayServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        Connection conn=(Connection) getServletContext().getAttribute("conn");
        String  id=request.getParameter("id");
        String title=request.getParameter("title");
        String content=request.getParameter("content");
        new Utils().updateEssay(conn,id,title,content);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

}
