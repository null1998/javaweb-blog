import com.sduhyd.blog.User;
import com.sduhyd.blog.Utils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=utf-8");
            HttpSession session = request.getSession(false);
            String result = "";
            String username=request.getParameter("username");
            String password=request.getParameter("password");
            User user= Utils.login(username,password);


            response.sendRedirect(request.getContextPath() + "/index.jsp");
            if(user != null){
                session.setAttribute("current-user", username);
                session.setAttribute("current-user_id",user.getId());
                result="login";
                System.out.println(user.getUsername()+" "+result);
            }else {
                System.out.println("登录失败！");
            }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }
}
