package com.sduhyd.blog.controller.initpageabout;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.model.SortUtils;
import com.sduhyd.blog.model.Utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;


public class BlogMainPageServlet extends HttpServlet {
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
        if (!arrayList.isEmpty()) {
            Essay[]all_essays=new SortUtils().reverseEssay(arrayList);
            Essay[]top_essays=new SortUtils().sortEssay(arrayList);
            synchronized (request.getServletContext()){
                context.setAttribute("all_essays",all_essays);
                context.setAttribute("top_essays",top_essays);
            }
        }

        response.sendRedirect(request.getContextPath()+"/page/main.jsp");
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
