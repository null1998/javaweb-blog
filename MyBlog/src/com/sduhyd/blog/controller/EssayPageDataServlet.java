package com.sduhyd.blog.controller;

import com.sduhyd.blog.bean.Comment;
import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.model.EssayOP;
import com.sduhyd.blog.model.SortUtils;
import com.sduhyd.blog.model.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//除了继承BlogDataServlet，还能载入单独博客界面所需的数据，并且作为/essayabout里所有servlet基类，方便其获取数据
@WebServlet(name = "EssayPageDataServlet")
public class EssayPageDataServlet extends BlogDataServlet {
    protected Integer essay_id;
    protected Essay essay;
    protected Comment sort_comments[];
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void loadEssayPageData(HttpServletRequest request, HttpServletResponse response,String FLAG)throws ServletException, IOException{
        loadContextData(request,response);
        loadSessionData(request,response);
        essay_id=Integer.valueOf(request.getParameter("essay_id"));
        for(int i=0;i<essays.length;i++){
            if(essays[i].getId().equals(essay_id)){
                if(FLAG.equals("star")){
                    essay=new Utils().star(conn,essay_id,current_user.getId(),essays[i]);
                }else if(FLAG.equals("diss")){
                    essay=new Utils().diss(conn,essay_id,current_user.getId(),essays[i]);
                }else if(FLAG.equals("favorite")){
                    essay=new EssayOP().favorite(conn,essay_id,current_user.getId(),essays[i]);
                }else if(FLAG.equals("comment")){
                    essay=new Utils().comments(conn,essay_id,essays[i]);
                    new Utils().WriteComments(conn,essay_id,request.getParameter("comment"),current_user);
                }else if(FLAG.equals("disCom")){
                    Integer comment_id=Integer.valueOf(request.getParameter("comment_id"));
                    essay=new Utils().disCom(conn,comment_id,current_user.getId(),essays[i]);
                }else if(FLAG.equals("starCom")){
                    Integer comment_id=Integer.valueOf(request.getParameter("comment_id"));
                    essay=new Utils().starCom(conn,comment_id,current_user.getId(),essays[i]);
                }
            }
        }
        Comment[]comments=new Utils().getComments(conn,essay_id);
        sort_comments=new SortUtils().sortCom(comments);
    }
}
