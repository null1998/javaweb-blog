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
    private Connection conn=null;
    private Utils utils=null;
    @Override
    public void init() throws ServletException {
        super.init();
        utils =new Utils();
        conn=utils.connection();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session= request.getSession(false);
        Integer user_id=(Integer) session.getAttribute("current-user_id");
        String username = (String)session.getAttribute("current-user");
        String title = request.getParameter("create_title");
        String article = request.getParameter("create_article");
        Date modify_time=new Date();
        utils.createEssay(conn,user_id,title,article,modify_time,username);
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }
    @Override
    public void destroy() {
        super.destroy();
        utils.releaseConnection(conn);
    }
}
