package com.sduhyd.blog.controller.userabout;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.controller.data.LoadBlogDataServlet;
import com.sduhyd.blog.model.SortUtils;
import com.sduhyd.blog.model.UserOP;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "MyFavoriteServlet")
public class MyFavoriteServlet extends LoadBlogDataServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        ArrayList<Essay> arrayList=new UserOP().myFavorite(conn,current_user.getId());
        if (!arrayList.isEmpty()) {
            Essay[] myfavorite_essays=new SortUtils().reverseEssay(arrayList);
            request.setAttribute("myfavorite_essays",myfavorite_essays);
        }
        request.getRequestDispatcher("/page/myFavorite.jsp").forward(request,response);
    }
}
