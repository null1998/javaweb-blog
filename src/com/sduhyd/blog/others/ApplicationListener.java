package com.sduhyd.blog.others;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.utils.SQL_JDBC;
import com.sduhyd.blog.utils.JdbcPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        JdbcPool.initPool();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        JdbcPool.destroyPool();
    }
}
