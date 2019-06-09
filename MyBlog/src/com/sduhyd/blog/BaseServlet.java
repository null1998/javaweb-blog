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


/**
 *在init()里利用反射将所有用得到的Class和Method存在数组里，这样每个请求都可以使用该servlet实例里公有的成员变量，
 * 不用每次请求都反射，除非该servlet生命周期结束，重新初始化。
 * 把路径写成类似/BaseServlet/UserActionProxy/login 这种类名+方法名的格式，把BaseServlet的url-pattern 改为/BaseServlet/*这样可以匹配
 * 各种类和方法。利用getPathInfo()地址，处理后再利用反射执行各种方法。
 */
@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {

    private ControllerMapper mapper;
    @Override

    public void init()throws ServletException{
        super.init();
        //"com.sduhyd.blog.controller"
        String controllerPackage = this.getInitParameter("controller-package");
        mapper = new ControllerMapper(controllerPackage);
        try {
            mapper.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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
