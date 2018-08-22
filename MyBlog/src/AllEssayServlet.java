import com.sduhyd.blog.Essay;
import com.sduhyd.blog.Utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "AllEssayServlet")
public class AllEssayServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Essay> arrayList = new ArrayList<>();
        arrayList= Utils.allEssay();
        ServletContext context = request.getServletContext();
        context.setAttribute("allEssay",arrayList);
        response.sendRedirect(request.getContextPath()+"/allblog.jsp");
    }
}
