package com.sduhyd.blog.daoImp;

import com.sduhyd.blog.utils.SQL_JDBC;

import java.util.List;
import java.util.Map;

public class UserEssayDaoImpl {
    public static void insert(String tableName, Object user_id,Object essay_id){
        String sql="insert into BLOG_TB_ESSAY_" + tableName + "(user_id,essay_id)values(?,?)";
        SQL_JDBC.prepareStatement(sql,user_id,essay_id);
    }

    public static List<Map<String,Object>> read(String tableName, String attribute, Object value){
        String sql = "select *from BLOG_TB_ESSAY_" + tableName + " where "+attribute+" = ?";
        List<Map<String,Object>> list= SQL_JDBC.prepareStatement(sql,value);
        return list;
    }

    public static void delete(String tableName, Object user_id,Object essay_id){
        String sql2="delete from BLOG_TB_ESSAY_" + tableName + " where user_id=? and essay_id=?";
        SQL_JDBC.prepareStatement(sql2,user_id,essay_id);
    }
}
