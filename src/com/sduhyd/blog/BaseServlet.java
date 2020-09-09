package com.sduhyd.blog;

import com.sduhyd.blog.foundation.ControllerMapper;
import com.sduhyd.blog.utils.JdbcPool;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {

    private ControllerMapper mapper;
    @Override

    public void init()throws ServletException{
        super.init();

        String controllerPackage = this.getInitParameter("controller-package");
        mapper = new ControllerMapper(controllerPackage);
        try {
            //加载了控制器并
            mapper.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 本不应该重写service()，因为是由service决定调用doGet和doPost
     * 但是自定义了controller类而不是用Servlet作为控制器
     * 所以重写了service()
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            resp.setContentType("application/json;charset=UTF-8");
            this.mapper.getHandler(req).handle(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().print("{\"code: 500\", \"message\": \"Internal Server Error\"}");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




    }

}
