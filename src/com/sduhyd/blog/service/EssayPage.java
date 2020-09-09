package com.sduhyd.blog.service;

import com.sduhyd.blog.bean.Comment;
import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.daoImp.CommentDaoImpl;
import com.sduhyd.blog.daoImp.EssayDaoImpl;
import com.sduhyd.blog.utils.BeanUtils;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;

public class EssayPage {
    /**
     * 取出特定的一篇文章
     * @param essay_id
     * @return
     */
   public static Essay getEssay(Integer essay_id){
       List<Map<String,Object>> list= EssayDaoImpl.read("id",essay_id);
       List<Object> essayList= BeanUtils.setAll(Essay.class,list);
       return (Essay) essayList.get(0);
    }

    /**
     * 取出特定文章所属的评论
     * @param essay_id
     * @return
     */
    public static List<Object> getComments(Integer essay_id){
        List<Map<String,Object>> list= CommentDaoImpl.read("essay_id",essay_id);
        List<Object> comments = BeanUtils.setAll(Comment.class,list);
        List<Comment> commentList = new ArrayList<>();
        for (Object object : comments) {
            commentList.add((Comment) object);
        }
        Collections.reverse(commentList);
        Collections.sort(commentList, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return o2.getStar().compareTo(o1.getStar());
            }
        });
        comments.clear();
        comments.addAll(commentList);
        return comments;
    }

}
