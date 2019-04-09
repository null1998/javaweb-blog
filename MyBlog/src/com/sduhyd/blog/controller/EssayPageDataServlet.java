package com.sduhyd.blog.controller;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EssayPageDataServlet")
public class EssayPageDataServlet extends BlogDataServlet {
    protected Essay essay;
    protected User current_user;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    void loadEssayPageData(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{

    }
}
