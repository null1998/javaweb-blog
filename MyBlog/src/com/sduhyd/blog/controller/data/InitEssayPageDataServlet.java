package com.sduhyd.blog.controller.data;

import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.model.SortUtils;
import com.sduhyd.blog.model.Utils;
import com.sduhyd.blog.bean.Comment;
import com.sduhyd.blog.bean.Essay;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用于初始化阅读博客界面的数据essay和sort_comments
 */

public class InitEssayPageDataServlet extends LoadBlogDataServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
             doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            initEssayPageData(request);
            request.getRequestDispatcher("/page/singleBlog.jsp").forward(request,response);

    }
    protected  void initEssayPageData(HttpServletRequest request){
        Comment[]comments=new Utils().getComments(conn,Integer.valueOf(request.getParameter("essay_id")));
        Comment[]sort_comments=new SortUtils().sortCom(comments);
        request.setAttribute("current_comments",sort_comments);
        for(int i=0;i<essays.length;i++){
            if(essays[i].getId().equals(Integer.valueOf(request.getParameter("essay_id")))){
                HttpSession session=request.getSession(false);;
                User current_user=(User)session.getAttribute("current_user");
                Essay essay=new Utils().visitor(conn,Integer.valueOf(request.getParameter("essay_id")),current_user.getId(),essays[i]);
                request.setAttribute("current_essay",essay);
            }
        }
    }
}