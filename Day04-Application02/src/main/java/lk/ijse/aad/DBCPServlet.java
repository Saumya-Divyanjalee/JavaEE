package lk.ijse.aad;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/dbcp")
public class DBCPServlet extends HttpServlet {
    BasicDataSource ds;

@Override
    public void init() throws ServletException {
    ServletContext sc = getServletContext();
    ds = (BasicDataSource) sc.getAttribute("datasource");
//        ds = new BasicDataSource();
//        ds.setDriverClassName("com.mysql.jdbc.Driver");
//        ds.setUrl("jdbc:mysql://localhost:3306/javaeeapp");
//        ds.setUsername("root");
//        ds.setPassword("root");
//        ds.setInitialSize(5);
//        ds.setMaxTotal(5);


    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try {

//            ds.setDriverClassName("com.mysql.jdbc.Driver");
//            ds.setUrl("jdbc:mysql://localhost:3306/javaeeapp");
//            ds.setUsername("root");
//            ds.setPassword("root");
//            ds.setInitialSize(5);
//            ds.setMaxTotal(5);
//
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection= DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/javaeeapp",
//                    "root","root");

            Connection connection = ds.getConnection();
            String query="INSERT INTO customer(id,name,address) VALUES (?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,address);
            int rowInserted=preparedStatement.executeUpdate();
            if (rowInserted>0){
                resp.getWriter().println("Customer saved successfully");
            }else {
                resp.getWriter().println("Customer not saved");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String id = req.getParameter("id");

        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<h2>Customer Data</h2>");
        html.append("<table border='1' cellpadding='10' style='border-collapse: collapse;'>");
        html.append("<tr><th>ID</th><th>Name</th><th>Address</th></tr>");

        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/javaeeapp", "root", "root");
            Connection connection = ds.getConnection();

            PreparedStatement ps;

            if (id == null) {
                ps = connection.prepareStatement("SELECT * FROM customer");
            } else {
                ps = connection.prepareStatement("SELECT * FROM customer WHERE id=?");
                ps.setString(1, id);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                html.append("<tr>");
                html.append("<td>").append(rs.getString("id")).append("</td>");
                html.append("<td>").append(rs.getString("name")).append("</td>");
                html.append("<td>").append(rs.getString("address")).append("</td>");
                html.append("</tr>");
            }

            html.append("</table>");
            html.append("</body></html>");

            resp.getWriter().print(html.toString());

            rs.close();
            ps.close();
            connection.close();

        } catch (Exception e) {
            resp.getWriter().println("Error: " + e.getMessage());
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection= DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/javaeeapp",
//                    "root","root");
            Connection connection = ds.getConnection();
            String query="update customer set name=?,address=? where id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,address);
            preparedStatement.setString(3,id);
            int rowInserted=preparedStatement.executeUpdate();
            if (rowInserted>0){
                resp.getWriter().println("Customer updated successfully");
            }else {
                resp.getWriter().println("Customer not updated");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection= DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/javaeeapp",
//                    "root","root");
            Connection connection = ds.getConnection();
            String query="delete from customer where id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            int rowDeleted=preparedStatement.executeUpdate();
            if (rowDeleted>0){
                resp.getWriter().println("Customer deleted successfully");
            }else {
                resp.getWriter().println("Customer not deleted");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


