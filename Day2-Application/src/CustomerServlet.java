import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    List<Customer> customerList= new ArrayList<Customer>();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("doPost");

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        customerList.add(new Customer(id,name,address));
        System.out.println("Customer added successfully");

//
//        String id = req.getParameter("id");
//        System.out.println("id:"+id);
//        String name = req.getParameter("name");
//        System.out.println("name:"+name);
//        String address = req.getParameter("address");
//        System.out.println("address:"+address);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        for(Customer c:customerList){
            resp.getWriter().println("<tr>" +
                    "<td>"+c.getId()+"</td>" +
                    "<td>"+c.getName()+"</td>" +
                    "<td>"+c.getAddress()+"</td>" +
                    "</tr>");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cid =req.getParameter("id");
        customerList.removeIf(c->c.getId().equals(cid));

    }
}
