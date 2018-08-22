import com.sduhyd.blog.Essay;
import com.sduhyd.blog.Utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ShowEssayServlet")
public class ShowEssayServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        //session.setAttribute("isShow",1);
        Essay[] essays=Utils.showEssay((Integer) session.getAttribute("current-user_id"));
        ServletContext context = request.getServletContext();
        context.setAttribute("essays", essays);
        response.sendRedirect(request.getContextPath()+"/blog.jsp");
    }
}
