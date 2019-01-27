package com.sduhyd.blog.bean;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class User implements HttpSessionBindingListener{
    private Integer id;
    private String username;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public void valueBound(HttpSessionBindingEvent event){
        HttpSession session=event.getSession();
        System.out.println(this.getUsername()+"绑定到会话："+session.getId());
    }
    public void valueUnbound(HttpSessionBindingEvent event){
        HttpSession session=event.getSession();
        System.out.println(this.getUsername()+"解除绑定："+session.getId());
    }

}
