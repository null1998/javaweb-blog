package com.sduhyd.blog.service;

import com.sduhyd.blog.bean.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;

/**
 * 暂时弃用
 */
public interface Action {
     public void login(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException;
     public  void register(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException;
     public void logOut(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException;
     public void showFavorite(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws ServletException,IOException;
     public void deleteFavorite(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException;
     public void createEssay(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws UnsupportedEncodingException,IOException;
     public void showMyEssays(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException;
     public  void  updateMyEssay(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws UnsupportedEncodingException;
     public Connection getConn( ServletConfig sc);
     public User getCurrentUser(HttpServletRequest request);
}
