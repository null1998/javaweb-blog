package com.sduhyd.blog.model;
import com.sduhyd.blog.bean.Essay;

import java.sql.*;
import java.util.ArrayList;

//每个ID对每个文章的部分操作是有次数限制的。
public class EssayIDManger extends UserIDLimit{
    boolean isVisitor(Connection conn, int essay_id,int user_id){
        String sql = "select  user_id from BLOG_TB_ESSAY_VISITOR where essay_id=?";
        return isOperate(conn,essay_id,user_id,sql);
    }
    boolean isStar(Connection conn, int essay_id,int user_id){
        String sql = "select  user_id from BLOG_TB_ESSAY_STAR where essay_id=?";
        return isOperate(conn,essay_id,user_id,sql);
    }
    boolean isDiss(Connection conn, int essay_id,int user_id){
        String sql = "select  user_id from BLOG_TB_ESSAY_DISS where essay_id=?";
        return isOperate(conn,essay_id,user_id,sql);
    }
    boolean isFavorite(Connection conn, int essay_id,int user_id){
        String sql = "select  user_id from BLOG_TB_ESSAY_FAVORITE where essay_id=?";
        return isOperate(conn,essay_id,user_id,sql);
    }
}
