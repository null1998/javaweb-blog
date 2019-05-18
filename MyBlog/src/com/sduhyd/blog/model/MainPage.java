package com.sduhyd.blog.model;

import com.sduhyd.blog.bean.Essay;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

public class MainPage {
    public void getData(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException {
        ServletContext context = request.getServletContext();
        Connection conn=(Connection) context.getAttribute("conn");
        ArrayList<Essay> arrayList = new Utils().allEssay(conn);
        Essay[]essays=new SortUtils().reverseEssay(arrayList);
        Essay[]top_essays=new SortUtils().sortEssay(arrayList);
        synchronized (request.getServletContext()){
            context.setAttribute("all_essays",essays);
            context.setAttribute("top_essays",top_essays);
        }
        response.sendRedirect("/page/main.jsp");
    }
}
