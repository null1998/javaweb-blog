package com.sduhyd.blog;

import com.sduhyd.blog.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;


public class CreateEssayServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        User current_user=null;
        synchronized (request.getSession(false)){
            HttpSession session=request.getSession(false);;
            current_user=(User)session.getAttribute("current_user");
        }
        Connection conn=(Connection) getServletContext().getAttribute("conn");
        Integer user_id=current_user.getId();
        String username = current_user.getUsername();
        String title = request.getParameter("create_title");
        String article = request.getParameter("create_article");
        Date modify_time=new Date();
        new Utils().createEssay(conn,user_id,title,article,modify_time,username);
        response.sendRedirect(request.getContextPath()+"/page/main.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
    @Override
    public void destroy() {
        super.destroy();
    }
}
