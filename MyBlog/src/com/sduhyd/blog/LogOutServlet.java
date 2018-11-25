package com.sduhyd.blog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;


public class LogOutServlet extends HttpServlet {
    private Connection conn=null;
    private Utils utils=null;
    @Override
    public void init() throws ServletException {
        super.init();
        utils =new Utils();
        conn=utils.connection();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
    @Override
    public void destroy() {
        super.destroy();
        utils.releaseConnection(conn);
    }
}
