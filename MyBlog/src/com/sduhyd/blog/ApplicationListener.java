package com.sduhyd.blog;

import com.sduhyd.blog.ConnctionDB;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
          ServletContext sc=sce.getServletContext();
          String url=sc.getInitParameter("url-database");
          Connection conn= new ConnctionDB().connection(url);
          sc.setAttribute("conn",conn);
    }

    public void contextDestroyed(ServletContextEvent sce) {
          ServletContext sc=sce.getServletContext();
          Connection conn=(Connection) sc.getAttribute("conn");
          ConnctionDB connDB=new ConnctionDB();
          connDB.releaseConnection(conn);
    }
}
