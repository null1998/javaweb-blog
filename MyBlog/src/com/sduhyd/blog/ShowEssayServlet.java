package com.sduhyd.blog;

import com.sduhyd.blog.Essay;
import com.sduhyd.blog.Utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;


public class ShowEssayServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        User current_user=null;
        synchronized (request.getSession(false)){
            HttpSession session = request.getSession(false);
            current_user=(User)session.getAttribute("current_user");
        }
        Connection conn=(Connection) getServletContext().getAttribute("conn");
        Integer current_user_id=current_user.getId();
        Essay[] essays=new Utils().showEssay(conn,current_user_id);
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
