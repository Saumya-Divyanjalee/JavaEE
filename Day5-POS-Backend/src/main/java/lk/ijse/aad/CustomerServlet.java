package lk.ijse.aad;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/api/v1/customer")
public class CustomerServlet extends HttpServlet {
    private DataSource ds;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ds = (BasicDataSource) servletContext.getAttribute("datasource");
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            JsonObject customer = gson.fromJson(request.getReader(), JsonObject.class);
            String id = customer.get("cid").getAsString();
            String name = customer.get("cname").getAsString();
            String address = customer.get("caddress").getAsString();

            connection = ds.getConnection();
            String query = "INSERT INTO Customer (id, name, address) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, address);

            int rowInserted = preparedStatement.executeUpdate();

            response.setContentType("application/json;charset=UTF-8");
            JsonObject jsonResponse = new JsonObject();

            if (rowInserted > 0) {
                jsonResponse.addProperty("status", "success");
                jsonResponse.addProperty("message", "Customer saved successfully");
            } else {
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "Customer not saved");
            }

            response.getWriter().println(jsonResponse.toString());

        } catch (SQLException e) {
            handleError(response, "Database error: " + e.getMessage());
        } finally {
            closeResources(connection, preparedStatement, null);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            JsonObject customer = gson.fromJson(request.getReader(), JsonObject.class);
            String id = customer.get("cid").getAsString();
            String name = customer.get("cname").getAsString();
            String address = customer.get("caddress").getAsString();

            connection = ds.getConnection();
            String query = "UPDATE Customer SET name=?, address=? WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, id);

            int rowUpdated = preparedStatement.executeUpdate();

            response.setContentType("application/json;charset=UTF-8");
            JsonObject jsonResponse = new JsonObject();

            if (rowUpdated > 0) {
                jsonResponse.addProperty("status", "success");
                jsonResponse.addProperty("message", "Customer updated successfully");
            } else {
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "Customer not found");
            }

            response.getWriter().println(jsonResponse.toString());

        } catch (SQLException e) {
            handleError(response, "Database error: " + e.getMessage());
        } finally {
            closeResources(connection, preparedStatement, null);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String id = request.getParameter("cid");

            connection = ds.getConnection();
            String query = "DELETE FROM Customer WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);

            int rowDeleted = preparedStatement.executeUpdate();

            response.setContentType("application/json;charset=UTF-8");
            JsonObject jsonResponse = new JsonObject();

            if (rowDeleted > 0) {
                jsonResponse.addProperty("status", "success");
                jsonResponse.addProperty("message", "Customer deleted successfully");
            } else {
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "Customer not found");
            }

            response.getWriter().println(jsonResponse.toString());

        } catch (SQLException e) {
            handleError(response, "Database error: " + e.getMessage());
        } finally {
            closeResources(connection, preparedStatement, null);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            JsonArray customerList = new JsonArray();
            connection = ds.getConnection();
            String query = "SELECT * FROM Customer ORDER BY id";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");

                JsonObject customer = new JsonObject();
                customer.addProperty("cid", id);
                customer.addProperty("cname", name);
                customer.addProperty("caddress", address);
                customerList.add(customer);
            }

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(customerList.toString());

        } catch (SQLException e) {
            handleError(response, "Database error: " + e.getMessage());
        } finally {
            closeResources(connection, preparedStatement, resultSet);
        }
    }

    private void handleError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        JsonObject errorResponse = new JsonObject();
        errorResponse.addProperty("status", "error");
        errorResponse.addProperty("message", message);
        response.getWriter().println(errorResponse.toString());
    }

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}