package com.sduhyd.blog.others;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnctionDB {

    public Connection connection(String url_database){
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://"+url_database+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String user = "test";
        String password = "12345";
        Connection conn=null;
        try{
            Class.forName(driver); //classLoader,加载对应驱动
            System.out.println("数据库连接中...");
            conn = (Connection) DriverManager.getConnection(url, user, password);
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
}
