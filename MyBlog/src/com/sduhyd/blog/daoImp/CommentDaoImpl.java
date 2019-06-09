package com.sduhyd.blog.daoImp;

import com.sduhyd.blog.utils.SQL_JDBC;

import java.util.List;
import java.util.Map;

public class CommentDaoImpl {
    /*
    CRUD的R
     */

    public static List<Map<String,Object>> read(String attribute, Object value){
        String sql="select *from BLOG_TB_COMMENT where "+attribute+"=?";
        List<Map<String,Object>> list= SQL_JDBC.prepareStatement(sql,value);
        return list;
    }

    public static  boolean insert(Object...objects){
        if(objects.length==7){
            String sql="insert into BLOG_TB_COMMENT(user_id,username,essay_id,creation_time,content,star,diss)values(?,?,?,?,?,?,?,?,?,?,?) ";
            SQL_JDBC.prepareStatement(sql,objects);
            return true;
        }
        return false;
    }
}
