package com.sduhyd.blog.controller.data;

import com.sduhyd.blog.bean.Comment;
import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.model.EssayOP;
import com.sduhyd.blog.model.SortUtils;
import com.sduhyd.blog.model.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *该类继承了LoadBlogDataServlet
 * 因此它可以获取ServletContext和HttpSession里的数据
 *  同时它本身也作为基类
 *  主要的作用是通过updateAndLoadEssayPageData(HttpServletRequest,HttpServletResponse,String)方法
 *  在完成点赞、评论、收藏、评论等操作后，更新阅读博客界面的数据 essay和sort_comments
 */



public class UpdateAndLoadEssayPageDataServlet extends LoadBlogDataServlet {
    protected Integer essay_id;
    protected Essay essay;
    protected Comment sort_comments[];


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        updateAndLoadEssayPageData(request,request.getParameter("FLAG"));
        request.getRequestDispatcher("/page/singleBlog.jsp").forward(request,response);
    }
    protected void updateAndLoadEssayPageData(HttpServletRequest request,String FLAG){
        essay_id=Integer.valueOf(request.getParameter("essay_id"));
        for(int i=0;i<essays.length;i++){
            if(essays[i].getId().equals(essay_id)){
                if(FLAG.equals("STAR")){
                    essay=new Utils().star(conn,essay_id,current_user.getId(),essays[i]);
                }else if(FLAG.equals("DISS")){
                    essay=new Utils().diss(conn,essay_id,current_user.getId(),essays[i]);
                }else if(FLAG.equals("FAVORITE")){
                    essay=new EssayOP().favorite(conn,essay_id,current_user.getId(),essays[i]);
                }else if(FLAG.equals("COMMENT")){
                    essay=new Utils().comments(conn,essay_id,essays[i]);
                    new Utils().WriteComments(conn,essay_id,request.getParameter("comment"),current_user);
                }else if(FLAG.equals("DISSCOM")){
                    Integer comment_id=Integer.valueOf(request.getParameter("comment_id"));
                    essay=new Utils().disCom(conn,comment_id,current_user.getId(),essays[i]);
                }else if(FLAG.equals("STARCOM")){
                    Integer comment_id=Integer.valueOf(request.getParameter("comment_id"));
                    essay=new Utils().starCom(conn,comment_id,current_user.getId(),essays[i]);
                }
            }
        }
        Comment[]comments=new Utils().getComments(conn,essay_id);
        sort_comments=new SortUtils().sortCom(comments);

        request.setAttribute("current_essay",essay);
        request.setAttribute("current_comments",sort_comments);
    }
}
