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
    private Connection conn=null;
    private Utils utils=null;
    @Override
    public void init() throws ServletException {
        super.init();
        utils =new Utils();
        conn=utils.connection();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        ArrayList<Essay> arrayList = new ArrayList<>();
        arrayList= utils.allEssay(conn);
        ServletContext context = request.getServletContext();
        context.setAttribute("allEssay",arrayList);
        response.sendRedirect(request.getContextPath()+"/allblog.jsp");
    }

    @Override
    public void destroy() {
        super.destroy();
        utils.releaseConnection(conn);
    }
}
