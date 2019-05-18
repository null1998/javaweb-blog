package com.sduhyd.blog.model;

import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.model.Action;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;

/**
 * UserAction的代理类，与UserAction一样实现Action接口
 * 在构造函数里传入UserAction对象，调用UserAction的方法
 * 在调用前可以进行筛选拦截。
 */
public class UserActionProxy implements Action {
    private Action action;

    public UserActionProxy(Action action){
        this.action=action;
    }

    public void login(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException {
        action.login(request,response,sc);
    }
    public  void register(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException{
        action.register(request,response,sc);
    }
    public void logOut(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException{
        if(isLogin(request)) {
            action.logOut(request, response, sc);
        }
    }
    public void showFavorite(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws ServletException,IOException{
        if(isLogin(request)){
            action.showFavorite(request,response,sc);
        }
    }
    public void deleteFavorite(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException{
        if(isLogin(request)){
            action.deleteFavorite(request,response,sc);
        }
    }
    public void createEssay(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws UnsupportedEncodingException,IOException{
        if(isLogin(request)){
            action.createEssay(request,response,sc);
        }
    }
    public void showMyEssays(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws IOException{
        if(isLogin(request)){
            action.showMyEssays(request,response,sc);
        }
    }
    public  void  updateMyEssay(HttpServletRequest request, HttpServletResponse response, ServletConfig sc)throws UnsupportedEncodingException{
        if(isLogin(request)){
            action.updateMyEssay(request,response,sc);
        }
    }


    public boolean isLogin(HttpServletRequest request) {
        if(getCurrentUser(request).getId()!=0){
            return true;
        }
        return false;
    }
    public Connection getConn( ServletConfig sc){
        return action.getConn(sc);
    }
    public User getCurrentUser(HttpServletRequest request){
        return action.getCurrentUser(request);
    }
}
