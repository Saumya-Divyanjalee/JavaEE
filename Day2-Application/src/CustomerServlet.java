import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    private static final List<Customer> customers = new ArrayList<>();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain;charset=UTF-8");

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");


        if (id == null || name == null || address == null ||
                id.trim().isEmpty() || name.trim().isEmpty() || address.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("All fields are required");
            return;
        }

        // Check if customer with same ID already exists
        for (Customer c : customers) {
            if (c.getId().equals(id)) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                resp.getWriter().write("Customer with ID " + id + " already exists");
                return;
            }
        }

        customers.add(new Customer(id, name, address));
        resp.getWriter().write("Customer Saved Successfully");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        if (customers.isEmpty()) {
            resp.getWriter().println("No customers found.");
            return;
        }

        for (Customer c : customers) {
            resp.getWriter().println(
                    "<tr>" +
                            "<td>" + c.getId()+ "</td>" +
                            "<td>" + c.getName()+ "</td>" +
                            "<td>" + c.getAddress()+ "</td>" +
                            "</tr>"
            );
        }
    }

    // ---------------------- UPDATE (PUT) ----------------------
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain;charset=UTF-8");


        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");


        if (id == null || name == null || address == null ||
                id.trim().isEmpty() || name.trim().isEmpty() || address.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("All fields are required");
            return;
        }

        for (Customer c : customers) {
            if (c.getId().equals(id)) {
                c.setName(name);
                c.setAddress(address);
                resp.getWriter().write("Customer Updated Successfully");
                return;
            }
        }

        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.getWriter().write("Customer with ID " + id + " not found");
    }

    // ---------------------- DELETE ----------------------
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain;charset=UTF-8");

        String id = req.getParameter("id");

        if (id == null || id.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Customer ID is required");
            return;
        }

        Customer toRemove = null;
        for (Customer c : customers) {
            if (c.getId().equals(id)) {
                toRemove = c;
                break;
            }
        }

        if (toRemove == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Customer with ID " + id + " not found");
            return;
        }

        customers.remove(toRemove);
        resp.getWriter().write("Customer deleted successfully");
    }


}