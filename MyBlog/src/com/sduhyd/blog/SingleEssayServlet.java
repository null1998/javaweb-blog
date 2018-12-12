package com.sduhyd.blog;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "SingleEssayServlet")
public class SingleEssayServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
             doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            ArrayList<Essay> essays=null;
            synchronized (getServletContext()){
                ServletContext context=getServletContext();
                essays=(ArrayList<Essay>) context.getAttribute("all_essays");
            }
            for(int i=0;i<essays.size();i++){
               if(essays.get(i).getId().equals(Integer.valueOf(request.getParameter("id")))){
                   request.setAttribute("current_essay",essays.get(i));
                   System.out.println("插入"+essays.get(i).getId());
               }
            }
            request.getRequestDispatcher("/singleBlog.jsp").forward(request,response);

    }
}
