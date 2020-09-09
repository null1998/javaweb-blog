package com.sduhyd.blog.utils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQL_JDBC {
    //FLAG 'QUERY' executeQuery,'UPDATE' ececuteUpdate  //list中的数据按SQL中？的顺序存储
    public static List<Map<String,Object>> prepareStatement(String sql,Object...objects){
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;
        Connection conn=JdbcPool.getConnection();
        try{
            preparedStatement= conn.prepareStatement(sql);
            if(objects!=null){
                for(int i=0;i<objects.length;i++){
                     preparedStatement.setObject(i+1,objects[i]);
                }
            }
            if(sql.split(" ")[0].equals("select")){
                rs=preparedStatement.executeQuery();
                return ResultSetMap(rs);
            }else{
                preparedStatement.executeUpdate();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            close(preparedStatement);
            close(rs);
            JdbcPool.releaseConnection(conn);
        }
        return null;
    }
    public static List<Map<String,Object>> ResultSetMap(ResultSet resultSet){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        try{
           ResultSetMetaData rsmd=resultSet.getMetaData();
           while(resultSet.next()) {
               Map<String, Object> map = new HashMap<String, Object>();
               //遍历获取的所有字段，把字段名和值存入map
               for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                   map.put(rsmd.getColumnLabel(i), resultSet.getObject(i));
               }
               list.add(map);
           }
        }catch (SQLException e){
                e.printStackTrace();
        }finally {
            close(resultSet);
        }
        return list;
    }


    public  static void releaseConnection(Connection conn){
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void close(PreparedStatement preparedStatement) {
        if(preparedStatement != null) {            //判断是否关闭，未关闭则进行关闭
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(ResultSet resultSet) {
        if(resultSet != null) {            //判断是否关闭，未关闭则进行关闭
            try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
