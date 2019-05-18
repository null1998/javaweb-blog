package com.sduhyd.blog.model;

import com.sduhyd.blog.bean.Comment;
import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

public class EssayPage {
   public void getData(HttpServletRequest request,HttpServletResponse response, ServletConfig sc)throws ServletException,IOException {
       Essay[] essays;
       Connection conn=(Connection) sc.getServletContext().getAttribute("conn");
       synchronized (sc.getServletContext()){
           ServletContext context=sc.getServletContext();
           essays=(Essay[]) context.getAttribute("all_essays");
       }
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
        request.getRequestDispatcher("/page/singleBlog.jsp").forward(request,response);

    }
    public void setData(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws ServletException,IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        Integer essay_id=Integer.valueOf(request.getParameter("essay_id"));
        Essay[] essays=(Essay[]) sc.getServletContext().getAttribute("all_essays");
        Connection conn=(Connection) sc.getServletContext().getAttribute("conn");
        HttpSession session=request.getSession(false);
        User current_user=(User)session.getAttribute("current_user");
        Essay essay=null;
        String FLAG=request.getParameter("FLAG");
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
        Comment[]sort_comments=new SortUtils().sortCom(comments);

        request.setAttribute("current_essay",essay);
        request.setAttribute("current_comments",sort_comments);

        request.getRequestDispatcher("/page/singleBlog.jsp").forward(request,response);
    }
}
