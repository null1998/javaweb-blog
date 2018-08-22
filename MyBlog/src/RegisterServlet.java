import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.sduhyd.blog.User;
import com.sduhyd.blog.Utils;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String result = "";
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        User user=Utils.register(username,password);
        response.sendRedirect("/index.jsp");
        if(user != null) {
            result="register";
            System.out.println(user.getUsername()+" "+result);
        }else {
            System.out.println("注册失败");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
