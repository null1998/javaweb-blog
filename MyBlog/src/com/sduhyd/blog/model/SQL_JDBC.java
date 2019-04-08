package com.sduhyd.blog.model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SQL_JDBC {
    public Connection connection(String url_database,String db_user,String password){
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://"+url_database+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        Connection conn=null;
        try{
            Class.forName(driver); //classLoader,加载对应驱动
            System.out.println("数据库连接中...");
            conn = (Connection) DriverManager.getConnection(url, db_user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("数据库连接成功！");
        return conn;
    }
    public  void releaseConnection(Connection conn){
        System.out.println("准备关闭数据库连接");
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("数据库断开连接成功！");
            }
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //FLAG 0 executeQuery,1 ececuteUpdate  //list中的数据按SQL中？的顺序存储
    public ResultSet prepareStatement(String FLAG,Connection conn,String sql, ArrayList list){
        ResultSet rs=null;
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            //如果是Statement而不是PrepareStatement的话，list为空
            if(list!=null){
                for(int i=0;i<list.size();i++){
                    if(list.get(i).getClass().toString().equals("class java.lang.Integer")){
                        statement.setInt(i+1,Integer.valueOf(list.get(i).toString()));
                    }else if(list.get(i).getClass().toString().equals("class java.lang.String")){
                        statement.setString(i+1,list.get(i).toString());
                    }else if(list.get(i).getClass().toString().equals("class java.util.Date")){
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        java.util.Date date= sdf.parse(list.get(i).toString());
                        statement.setDate(i+1,new java.sql.Date(date.getTime()));
                    }
                }
            }
            if(FLAG.equals("QUERY")){
                rs=statement.executeQuery();
            }else if(FLAG.equals("UPDATE")){
                statement.executeUpdate();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

}
