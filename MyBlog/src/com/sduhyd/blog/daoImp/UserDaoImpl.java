package com.sduhyd.blog.daoImp;

import com.sduhyd.blog.utils.SQL_JDBC;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class UserDaoImpl {

    /**
     * CRUD的R
     */

    /**
     * where只有一个值的
     * @param attribute
     * @param value
     * @return
     */
    public static List<Map<String,Object>> read(String attribute,Object value){
        String sql = "select *from BLOG_TB_USER where "+attribute+" = ?";
        List<Map<String,Object>> list=SQL_JDBC.prepareStatement(sql,value);
        return list;
    }

    public static void insert(String username,String password){
        String sql="insert into BLOG_TB_USER(username,password)values(?,?)";
        SQL_JDBC.prepareStatement(sql,username,password);
    }
}
