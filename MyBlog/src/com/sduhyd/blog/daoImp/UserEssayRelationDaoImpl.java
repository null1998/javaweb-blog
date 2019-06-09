package com.sduhyd.blog.daoImp;

import com.sduhyd.blog.utils.SQL_JDBC;

import java.util.List;
import java.util.Map;

public class UserEssayRelationDaoImpl {
    /**
     * User与Essay的favorite联系
     */

    /**
     * CRUD的R
     */
    public static List<Map<String,Object>> readFavorite(String attribute, Object value){
        String sql = "select *from BLOG_TB_ESSAY_FAVORITE where "+attribute+" = ?";
        List<Map<String,Object>> list= SQL_JDBC.prepareStatement(sql,value);
        return list;
    }
    /**
     * CRUD的D
     */
    public static void deleteFavorite(Object user_id,Object essay_id){
        String sql2="delete from BLOG_TB_ESSAY_FAVORITE where user_id=? and essay_id=?";
        SQL_JDBC.prepareStatement(sql2,user_id,essay_id);
    }


    /**
     * User与Essay的visitor联系
     */

    /**
     * CRUD的R
     */
    public static List<Map<String,Object>> readVisitor(Object user_id,Object essay_id){
        String sql = "select *from BLOG_TB_ESSAY_VISITOR where user_id=? and essay_id=?";
        List<Map<String,Object>> list= SQL_JDBC.prepareStatement(sql,user_id,essay_id);
        return list;
    }

    public static void insert(Object user_id,Object essay_id){
        String sql="insert into BLOG_TB_ESSAY_VISITOR(user_id,essay_id)values(?,?)";
        SQL_JDBC.prepareStatement(sql,user_id,essay_id);
    }
}
