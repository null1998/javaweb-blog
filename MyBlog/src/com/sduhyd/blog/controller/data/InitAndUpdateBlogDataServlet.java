package com.sduhyd.blog.controller.data;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.model.SortUtils;
import com.sduhyd.blog.model.Utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * 用于初始化和更新全局数据的Servlet
 */


public class InitAndUpdateBlogDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateContextData(request);
        response.sendRedirect("/mainpage");
    }
    //第一次调用时init，后面再调用就是更新
    protected void updateContextData(HttpServletRequest request){
        ServletContext context = request.getServletContext();
        Connection conn=(Connection) context.getAttribute("conn");
        ArrayList<Essay> arrayList = new Utils().allEssay(conn);
        Essay[]essays=new SortUtils().reverseEssay(arrayList);
        Essay[]top_essays=new SortUtils().sortEssay(arrayList);
        synchronized (request.getServletContext()){
            context.setAttribute("all_essays",essays);
            context.setAttribute("top_essays",top_essays);
        }

    }
}
