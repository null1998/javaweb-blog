package com.sduhyd.blog;

import com.sduhyd.blog.Utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import java.sql.Connection;

public class ApplicationListener implements ServletContextListener {
    private Connection conn=null;
    private Utils utils=null;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        utils=new Utils();
//        System.out.println("正在连接数据库.......");
//        utils.connection();
//        System.out.println("数据库连接成功！");
    }

    public void contextDestroyed(ServletContextEvent sce) {
//        System.out.println("准备关闭数据库连接");
//        utils.releaseConnection(conn);
//        System.out.println("数据库连接关闭完成");
    }
}
