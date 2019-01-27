package com.sduhyd.blog.controller.essayabout;

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
public class CommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        User current_user=null;
        Essay[] essays=null;
        Connection conn=(Connection) getServletContext().getAttribute("conn");
        synchronized (request.getSession(false)){
            HttpSession session=request.getSession(false);;
            current_user=(User)session.getAttribute("current_user");
        }
        synchronized (getServletContext()){
            ServletContext context=getServletContext();
            essays=(Essay[]) context.getAttribute("all_essays");
        }
        for(int i=0;i<essays.length;i++){
            if(essays[i].getId().equals(Integer.valueOf(request.getParameter("essay_id")))){
                Essay essay=new Utils().comments(conn,Integer.valueOf(request.getParameter("essay_id")),essays[i]);
                request.setAttribute("current_essay",essay);
            }
        }
        String commentContent=request.getParameter("comment");
        Integer essay_id=Integer.valueOf(request.getParameter("essay_id"));
        new Utils().WriteComments(conn,essay_id,commentContent,current_user);
        request.getRequestDispatcher("/essaypage").forward(request,response);
    }
}
