package com.sduhyd.blog.model;
import com.sduhyd.blog.bean.Essay;

import java.sql.*;

//每个ID对每个文章的部分操作是有次数限制的。
public class EssayIDManger {

    //这个id是否已经访问过了这篇文章
    boolean isVisitor(Connection conn, int essay_id,int user_id){
        //无账号登陆视为已访问，不增加访问量
         if(user_id==0){
             return true;
         }
         try {
             String sql = "select  user_id from BLOG_TB_VISITE where essay_id=?";
             PreparedStatement statement = conn.prepareStatement(sql);
             statement.setInt(1, essay_id);
             ResultSet rs = statement.executeQuery();
             while(rs.next()){
                 //如果有则视为已访问
                 if(rs.getInt("user_id")==user_id){
                     rs.close();
                     statement.close();
                     return true;
                 }
             }
             rs.close();
             statement.close();
         }catch(SQLException e){
             e.printStackTrace();
         }
        return false;
    }
    boolean isStar(Connection conn, int essay_id,int user_id){
        //无账号登陆视为已点赞
        if(user_id==0){
            return true;
        }
        try {
            String sql = "select  user_id from BLOG_TB_ESSAY_STAR where essay_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, essay_id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                if(rs.getInt("user_id")==user_id){
                    rs.close();
                    statement.close();
                    return true;
                }
            }
            rs.close();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    boolean isDiss(Connection conn, int essay_id,int user_id){
        if(user_id==0){
            return true;
        }
        try {
            String sql = "select  user_id from BLOG_TB_ESSAY_DISS where essay_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, essay_id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                if(rs.getInt("user_id")==user_id){
                    rs.close();
                    statement.close();
                    return true;
                }
            }
            rs.close();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    boolean isFavorite(Connection conn, int essay_id,int user_id){
        //无账号登陆视为已点赞
        if(user_id==0){
            return true;
        }
        try {
            String sql = "select  user_id from BLOG_TB_ESSAY_FAVORITE where essay_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, essay_id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                if(rs.getInt("user_id")==user_id){
                    rs.close();
                    statement.close();
                    return true;
                }
            }
            rs.close();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
