package com.sduhyd.blog.controller;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.model.SortUtils;
import com.sduhyd.blog.model.Utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

@WebServlet(name = "BlogDataServlet")
public class BlogDataServlet extends HttpServlet {
    protected Essay[]essays=null;
    protected Essay[]top_essays=null;
    protected Connection conn;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void loadContextData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        conn=(Connection) getServletContext().getAttribute("conn");
        synchronized (getServletContext()){
            ServletContext context=getServletContext();
            essays=(Essay[]) context.getAttribute("all_essays");
            top_essays=(Essay[]) context.getAttribute("top_essays");
        }
    }
}
