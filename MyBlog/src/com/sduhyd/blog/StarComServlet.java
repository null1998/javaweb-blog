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

@WebServlet(name = "StarComServlet")
public class StarComServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Essay[] essays=null;
        Connection conn=(Connection) getServletContext().getAttribute("conn");
        synchronized (getServletContext()){
            ServletContext context=getServletContext();
            essays=(Essay[]) context.getAttribute("all_essays");
        }
        for(int i=0;i<essays.length;i++){
            if(essays[i].getId().equals(Integer.valueOf(request.getParameter("essay_id")))){
                request.setAttribute("current_essay",essays[i]);
            }
        }

        Integer comment_id=Integer.valueOf(request.getParameter("comment_id"));
        new Utils().starCom(conn,comment_id);
        Comment[]comments=new Utils().getComments(conn,Integer.valueOf(request.getParameter("essay_id")));
        Comment[]sort_comments=new SortUtils().sortCom(comments);
        request.setAttribute("current_comments",sort_comments);
        request.getRequestDispatcher("/page/singleBlog.jsp").forward(request,response);
    }
}
