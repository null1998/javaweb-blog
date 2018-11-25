package com.sduhyd.blog;

import com.sduhyd.blog.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


public class UpdateEssayServlet extends HttpServlet {
    private Connection conn=null;
    private Utils utils;
    @Override
    public void init() throws ServletException {
        super.init();
        utils =new Utils();
        conn=utils.connection();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String  id=request.getParameter("id");
        String title=request.getParameter("title");
        String content=request.getParameter("content");
        utils.updateEssay(conn,id,title,content);
    }
    @Override
    public void destroy() {
        super.destroy();
        utils.releaseConnection(conn);
    }
}
