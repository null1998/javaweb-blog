package com.sduhyd.blog.controller;

import com.sduhyd.blog.annotations.AuthCheck;
import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.foundation.RequestMethod;
import com.sduhyd.blog.foundation.annotations.Controller;
import com.sduhyd.blog.foundation.annotations.RequestMapping;
import com.sduhyd.blog.service.MainPage;
import com.sduhyd.blog.service.EssayPage;
import com.sduhyd.blog.service.UserAction;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller("page")
public class PageController {
    // 这些方法不需要登录就能访问
    @RequestMapping(method = RequestMethod.ANY,value="loadMain")
    public void loadMain(HttpServletRequest request, HttpServletResponse response)throws IOException {
        ServletContext context = request.getServletContext();
        synchronized (request.getServletContext()){
            context.setAttribute("all_essays",MainPage.getAllEssays());
            //context.setAttribute("top_essays",top_essays);
        }
        response.sendRedirect("/page/main.jsp");
    }
    @RequestMapping(method = RequestMethod.GET,value="loadEssay")
    public void loadEssay(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
        Integer essay_id=Integer.valueOf(request.getParameter("essay_id"));
        User current_user=(User)request.getAttribute("current_user");
        if(current_user!=null){
            UserAction.visitor(current_user.getId(),essay_id);
        }
        Essay essay=EssayPage.getEssay(essay_id);
        List<Object> commentList=EssayPage.getComments(essay_id);

        request.setAttribute("current_essay",essay);
        request.setAttribute("current_comments",commentList);




        request.getRequestDispatcher("/page/singleBlog.jsp").forward(request,response);
    }
    @AuthCheck//必须是已登录的用户才能访问
    @RequestMapping(method = RequestMethod.ANY,value="updateEssay")
    public void updateEssay(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
       // new EssayPage().setData(request,response);
    }

}
