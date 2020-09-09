package com.sduhyd.blog.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * 连接池
 */
public class JdbcPool{

    /**
     * @Field: Connections
     *
     */
    private static CopyOnWriteArrayList conns = new CopyOnWriteArrayList();
    private static String driver,url,username,password;
    private static int jdbcPoolMaxActive;
    //活跃的conn连接
    private static volatile AtomicInteger activeCounter;

    public static void initPool(){
        //在静态代码块中加载db.properties数据库配置文件
        //InputStream in = JdbcPool.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            FileInputStream in=new FileInputStream("C:/work/IdeaProjects/MyBlog/web/db.properties");;
            Properties prop = new Properties();
            prop.load(in);
            driver = prop.getProperty("driver");
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            System.out.println(driver+url+username+password);
            //数据库连接池的初始化连接数大小
            int jdbcPoolInitSize =Integer.parseInt(prop.getProperty("jdbcPoolInitSize"));
            jdbcPoolMaxActive=Integer.parseInt(prop.getProperty("jdbcPoolMaxActive"));
            //waitTime=Integer.parseInt(prop.getProperty("waitTime"));
            //加载数据库驱动
            Class.forName(driver);
            for (int i = 0; i < jdbcPoolInitSize; i++) {
                Connection conn = DriverManager.getConnection(url, username, password);
                //将获取到的数据库连接加入到Connections集合中，Connections此时就是一个存放了数据库连接的连接池
                conns.add(conn);
            }
            activeCounter = new AtomicInteger(0);
        } catch (SQLException e) {
            System.out.println(" 创建数据库连接失败！ " + e.getMessage());
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    /* 获取数据库连接
     * @see javax.sql.DataSource#getConnection()
     */

    public static Connection getConnection(){
        if (conns.size() > 0) {
            //从Connections集合中获取一个数据库连接
            final Connection conn = (Connection) conns.remove(0);
            activeCounter.incrementAndGet();
            //返回Connection对象的代理对象,防止不小心调用了close把conn真正地关闭了
            return (Connection) Proxy.newProxyInstance(JdbcPool.class.getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args)
                        throws Throwable {
                    if (!method.getName().equals("close")) {
                        return method.invoke(conn, args);
                    } else {
                        //如果调用的是Connection对象的close方法，就把conn还给数据库连接池
                        conns.add(conn);
                        activeCounter.decrementAndGet();
                        return null;
                    }
                }
            });
        }else if(activeCounter.get() < jdbcPoolMaxActive){//如果没有达到上限就创建新的连接并放入
            try {
                Class.forName(driver);
                Connection conn = DriverManager.getConnection(url, username, password);
                conns.add(conn);
                return getConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }else{
           throw new RuntimeException("请等待连接");
        }
        return null;
    }
    public static void releaseConnection(Connection conn){
        conns.add(conn);
        activeCounter.incrementAndGet();
    }
    public static void destroyPool(){
        while(!conns.isEmpty()){
            SQL_JDBC.releaseConnection((Connection) conns.remove(0));
        }
    }
}
