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

@WebServlet(name = "DisServlet")
public class DisServlet extends HttpServlet {
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
            if(essays[i].getId().equals(Integer.valueOf(request.getParameter("id")))){
                Essay essay=new Utils().diss(conn,Integer.valueOf(request.getParameter("id")),essays[i]);
                request.setAttribute("current_essay",essay);
            }
        }
        request.getRequestDispatcher("/page/singleBlog.jsp").forward(request,response);
    }
}
