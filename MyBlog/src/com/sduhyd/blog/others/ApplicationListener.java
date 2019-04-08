package com.sduhyd.blog.others;

import com.sduhyd.blog.model.SQL_JDBC;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;

public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
          ServletContext sc=sce.getServletContext();
          String url=sc.getInitParameter("url-database");
          Connection conn=new SQL_JDBC().connection(url,"test","12345");
          sc.setAttribute("conn",conn);
    }

    public void contextDestroyed(ServletContextEvent sce) {
          ServletContext sc=sce.getServletContext();
          Connection conn=(Connection) sc.getAttribute("conn");
          new SQL_JDBC().releaseConnection(conn);
    }
}
