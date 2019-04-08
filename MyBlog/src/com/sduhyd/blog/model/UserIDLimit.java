package com.sduhyd.blog.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserIDLimit {
    SQL_JDBC sql_jdbc=new SQL_JDBC();
    //当前ID是否对目标进行过某种操作
    boolean isOperate(Connection conn, int target_id, int user_id, String sql ){
        //无账号视为已操作
        if(user_id==0){
            return true;
        }
        ResultSet rs=null;
        ArrayList list=new ArrayList();
        list.add(target_id);
        rs=sql_jdbc.prepareStatement("QUERY",conn,sql,list);
        try {
            while(rs.next()){
                //如果有user_id则该账号已操作
                if(rs.getInt("user_id")==user_id){
                    rs.close();
                    return true;
                }
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
