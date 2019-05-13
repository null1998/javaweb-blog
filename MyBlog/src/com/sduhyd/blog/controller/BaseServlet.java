package com.sduhyd.blog.controller;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * 把路径写成类似/BaseServlet/UserAction/login 这种类名+方法名的格式，把BaseServlet的url-pattern 改为/BaseServlet/*这样可以匹配
 * 各种类和方法。利用getPathInfo()地址，处理后再利用反射执行各种方法。
 */
@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] cmd=request.getPathInfo().split("/");
        String className="com.sduhyd.blog.model."+cmd[1];
        String methodName=cmd[2];
        System.out.println(className);
        System.out.println(methodName);
        try{
            Class c=Class.forName(className);
            Method method= c.getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class, ServletConfig.class);
            method.invoke(c,request,response,getServletConfig());
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }



    }

}
