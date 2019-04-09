package com.sduhyd.blog.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentIDManger extends UserIDLimit{
    SQL_JDBC sql_jdbc=new SQL_JDBC();
    boolean isStar(Connection conn,int comment_id,int user_id){
        String sql = "select  user_id from BLOG_TB_COMMENT_STAR where comment_id=?";
        return isOperate(conn,comment_id,user_id,sql);
    }
    boolean isDiss(Connection conn,int comment_id,int user_id){
        String sql = "select  user_id from BLOG_TB_COMMENT_DISS where comment_id=?";
        return isOperate(conn,comment_id,user_id,sql);
    }
}
