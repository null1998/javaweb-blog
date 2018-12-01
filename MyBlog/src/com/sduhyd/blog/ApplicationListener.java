package com.sduhyd.blog;

import com.sduhyd.blog.ConnctionDB;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import java.sql.Connection;

public class ApplicationListener implements ServletContextListener {
    private Connection conn=null;
    private Utils utils=null;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
          ServletContext sc=sce.getServletContext();
          String url=sc.getInitParameter("url-database");
          Connection conn= new ConnctionDB(url).connection();
          sc.setAttribute("conn",conn);
    }

    public void contextDestroyed(ServletContextEvent sce) {
          ServletContext sc=sce.getServletContext();
          Connection conn=(Connection) sc.getAttribute("conn");
          new ConnctionDB().releaseConnection(conn);
    }
}
