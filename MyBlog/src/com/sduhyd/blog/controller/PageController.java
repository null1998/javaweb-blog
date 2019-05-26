package com.sduhyd.blog.controller;

import com.sduhyd.blog.annotations.AuthCheck;
import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.foundation.RequestMethod;
import com.sduhyd.blog.foundation.annotations.Controller;
import com.sduhyd.blog.foundation.annotations.RequestMapping;
import com.sduhyd.blog.model.EssayPage;
import com.sduhyd.blog.model.MainPage;
import com.sduhyd.blog.model.NullUser;

import javax.security.sasl.SaslException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller("page")
public class PageController {
    // 这些方法不需要登录就能访问
    @RequestMapping(method = RequestMethod.ANY,value="loadMain")
    public void loadMain(HttpServletRequest request, HttpServletResponse response)throws IOException {
        MainPage.getData(request,response);
    }
    @RequestMapping(method = RequestMethod.GET,value="loadEssay")
    public void loadEssay(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
        new EssayPage().getData(request,response);
    }
    @AuthCheck//必须是已登录的用户才能访问
    @RequestMapping(method = RequestMethod.ANY,value="updateEssay")
    public void updateEssay(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
        new EssayPage().setData(request,response);
    }

}
