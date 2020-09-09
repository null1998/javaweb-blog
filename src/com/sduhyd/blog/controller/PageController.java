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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller("page")
public class PageController {
    /**
     * 加载主页
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.ANY,value="loadMain")
    public void loadMain(HttpServletRequest request, HttpServletResponse response)throws IOException {
        ServletContext context = request.getServletContext();
        synchronized (request.getServletContext()){
            List<Object> all_essays = MainPage.reorderShowEssays();
            context.setAttribute("all_essays",all_essays);
            List<Object> top_essays =MainPage.showEssayOrderByStar(all_essays, 5);
            context.setAttribute("top_essays",top_essays);
        }
        response.sendRedirect("/page/main.jsp");
    }

    /**
     * 加载单篇文章
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.ANY,value="loadEssay")
    public void loadEssay(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
        Integer essay_id=Integer.valueOf(request.getParameter("essay_id"));
        HttpSession httpSession = request.getSession(false);
        User current_user=(User)httpSession.getAttribute("current_user");
        if(current_user != null){
            UserAction.visitorEssay(essay_id, current_user.getId());
        }
        Essay essay=EssayPage.getEssay(essay_id);
        List<Object> commentList=EssayPage.getComments(essay_id);
        request.setAttribute("current_essay",essay);
        request.setAttribute("current_comments",commentList);
        request.getRequestDispatcher("/page/singleBlog.jsp").forward(request,response);
    }





}
