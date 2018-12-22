package com.sduhyd.blog;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

@WebServlet(name = "SingleEssayServlet")
public class SingleEssayServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
             doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            Essay[]essays=null;
            Connection conn=(Connection) getServletContext().getAttribute("conn");
            synchronized (getServletContext()){
                ServletContext context=getServletContext();
                essays=(Essay[]) context.getAttribute("all_essays");
            }
            Comment[]comments=new Utils().getComments(conn,Integer.valueOf(request.getParameter("essay_id")));
            Comment[]sort_comments=new SortUtils().sortCom(comments);
            request.setAttribute("current_comments",sort_comments);
            for(int i=0;i<essays.length;i++){
               if(essays[i].getId().equals(Integer.valueOf(request.getParameter("essay_id")))){
                   Essay essay=new Utils().visitor(conn,Integer.valueOf(request.getParameter("essay_id")),essays[i]);
                   request.setAttribute("current_essay",essay);
               }
            }
            request.getRequestDispatcher("/page/singleBlog.jsp").forward(request,response);

    }
}
