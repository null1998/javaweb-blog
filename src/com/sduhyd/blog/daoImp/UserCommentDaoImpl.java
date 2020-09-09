package com.sduhyd.blog.daoImp;

import com.sduhyd.blog.utils.SQL_JDBC;

import java.util.List;
import java.util.Map;

/**
 * @author hyd
 * @date 2020/4/22 1:17
 */
public class UserCommentDaoImpl {
    public static void insert(String tableName, Object user_id,Object comment_id){
        String sql="insert into BLOG_TB_COMMENT_" + tableName + "(user_id,comment_id)values(?,?)";
        SQL_JDBC.prepareStatement(sql,user_id,comment_id);
    }

    public static List<Map<String,Object>> read(String tableName, String attribute, Object value){
        String sql = "select *from BLOG_TB_COMMENT_" + tableName + " where "+attribute+" = ?";
        List<Map<String,Object>> list= SQL_JDBC.prepareStatement(sql,value);
        return list;
    }

    public static void delete(String tableName, Object user_id,Object comment_id){
        String sql2="delete from BLOG_TB_COMMENT_" + tableName + " where user_id=? and comment_id=?";
        SQL_JDBC.prepareStatement(sql2,user_id,comment_id);
    }
}
