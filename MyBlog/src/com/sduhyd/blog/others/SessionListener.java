package com.sduhyd.blog.others;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se){
        HttpSession session=se.getSession();
        //System.out.println("开始会话："+session.getId());
    }
    public void sessionDestroyed(HttpSessionEvent se){
        HttpSession session=se.getSession();
       //System.out.println("撤销会话："+session.getId());
    }
}
