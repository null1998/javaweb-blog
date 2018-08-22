import com.sduhyd.blog.Utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("正在连接数据库.......");
        Utils.connection();
        System.out.println("数据库连接成功！");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("准备关闭数据库连接");
        Utils.releaseConnection();
        System.out.println("数据库连接关闭完成");
    }
}
