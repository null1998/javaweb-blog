package com.sduhyd.blog;

import com.sduhyd.blog.Essay;
import com.sduhyd.blog.Utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;


public class AllEssayServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        ServletContext  context = request.getServletContext();
        Connection conn=(Connection) context.getAttribute("conn");
        ArrayList<Essay> arrayList = new Utils().allEssay(conn);
        synchronized (request.getServletContext()){
            context.setAttribute("all_essays",arrayList);
        }
        response.sendRedirect(request.getContextPath()+"/allblog.jsp");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
