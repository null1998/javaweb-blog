package com.sduhyd.blog.controller.userabout;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.controller.data.LoadBlogDataServlet;
import com.sduhyd.blog.model.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//暂时没用
public class ShowEssayServlet extends LoadBlogDataServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Essay[] essays=new Utils().showEssay(conn,current_user.getId());
        synchronized (request.getSession(false)){
            HttpSession session = request.getSession(false);
            session.setAttribute("my_essays", essays);
        }
        response.sendRedirect(request.getContextPath()+"/blog.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
