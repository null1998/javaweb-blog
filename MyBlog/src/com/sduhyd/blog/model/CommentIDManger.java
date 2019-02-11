package com.sduhyd.blog.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentIDManger {
    boolean isStar(Connection conn,int user_id,int comment_id){
        if(user_id==0){
            return true;
        }
        try {
            String sql = "select  user_id from BLOG_TB_COMMENT_STAR where comment_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, comment_id);
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
    boolean isDiss(Connection conn,int user_id,int comment_id){
        if(user_id==0){
            return true;
        }
        try {
            String sql = "select  user_id from BLOG_TB_COMMENT_DISS where comment_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, comment_id);
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
