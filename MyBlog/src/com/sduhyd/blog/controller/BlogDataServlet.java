package com.sduhyd.blog.controller;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.model.SortUtils;
import com.sduhyd.blog.model.Utils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
* BlogDataServlet作为这个项目的一个重要基类，
* 重写了HttpServlet里的service(HttpServletRequest,HttpServletResponse)方法，因为
* 该方法里有用到maybeSetLastModified(HttpServletResponse,long)方法，而此方法在HttpServlet
* 里是私有的，所以也重写了maybeSetLastModified(HttpServletResponse,long)方法
* 主要的作用是通过loadContextData(HttpServletRequest,HttpServletResponse)
* 和loadSessionData(HttpServletRequest,HttpServletResponse)这两个方法
* 获取了ServletContext和HttpSession里的数据，
* 这样任何继承了此类的所有Servlet类在其准备执行doGet()和doPost时
* 便可以使用这些变量
* */
@WebServlet(name = "BlogDataServlet")
public class BlogDataServlet extends HttpServlet {
    protected Connection conn;
    protected Essay[]essays=null;
    protected Essay[]top_essays=null;
    protected User current_user;

    //maybeSetLastModified(HttpServletResponse,long)方法里有用到此变量
    private static final ResourceBundle lStrings = ResourceBundle.getBundle("javax.servlet.http.LocalStrings");

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //service(HttpServletRequest,HttpServletResponse)的重写只是在这里加了两个函数调用，其余不变。
        loadContextData();
        loadSessionData(req);

        String method = req.getMethod();
        long lastModified;
        if (method.equals("GET")) {
            lastModified = this.getLastModified(req);
            if (lastModified == -1L) {
                this.doGet(req, resp);
            } else {
                long ifModifiedSince;
                try {
                    ifModifiedSince = req.getDateHeader("If-Modified-Since");
                } catch (IllegalArgumentException var9) {
                    ifModifiedSince = -1L;
                }

                if (ifModifiedSince < lastModified / 1000L * 1000L) {
                    this.maybeSetLastModified(resp, lastModified);
                    this.doGet(req, resp);
                } else {
                    resp.setStatus(304);
                }
            }
        } else if (method.equals("HEAD")) {
            lastModified = this.getLastModified(req);
            this.maybeSetLastModified(resp, lastModified);
            this.doHead(req, resp);
        } else if (method.equals("POST")) {
            this.doPost(req, resp);
        } else if (method.equals("PUT")) {
            this.doPut(req, resp);
        } else if (method.equals("DELETE")) {
            this.doDelete(req, resp);
        } else if (method.equals("OPTIONS")) {
            this.doOptions(req, resp);
        } else if (method.equals("TRACE")) {
            this.doTrace(req, resp);
        } else {
            String errMsg = lStrings.getString("http.method_not_implemented");
            Object[] errArgs = new Object[]{method};
            errMsg = MessageFormat.format(errMsg, errArgs);
            resp.sendError(501, errMsg);
        }

    }
    private void maybeSetLastModified(HttpServletResponse resp, long lastModified) {
        if (!resp.containsHeader("Last-Modified")) {
            if (lastModified >= 0L) {
                resp.setDateHeader("Last-Modified", lastModified);
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void loadContextData(){
        conn=(Connection) getServletContext().getAttribute("conn");
        synchronized (getServletContext()){
            ServletContext context=getServletContext();
            essays=(Essay[]) context.getAttribute("all_essays");
            top_essays=(Essay[]) context.getAttribute("top_essays");
        }
    }
    protected void loadSessionData(HttpServletRequest request){
        HttpSession session=request.getSession(false);;
        current_user=(User)session.getAttribute("current_user");
    }
}
