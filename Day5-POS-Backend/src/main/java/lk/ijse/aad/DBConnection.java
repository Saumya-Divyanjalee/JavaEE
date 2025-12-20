package lk.ijse.aad;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.dbcp2.BasicDataSource;

@WebListener
public class DBConnection implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext =
                event.getServletContext();
        BasicDataSource ds;
        ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/pos73");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setInitialSize(50);
        ds.setMaxTotal(100);
        servletContext.setAttribute("datasource",ds);
    }
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Stopping ServletContextServlet");
    }
}