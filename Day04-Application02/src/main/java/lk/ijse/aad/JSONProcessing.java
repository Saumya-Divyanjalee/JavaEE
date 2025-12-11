package lk.ijse.aad;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/json")
public class JSONProcessing extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        JsonObject customer=gson.fromJson(req.getReader(), JsonObject.class);
        System.out.println(customer);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject customer = new JsonObject();
        customer.addProperty("name","saumya");
        customer.addProperty("age",24);
        customer.addProperty("contact","0752313179");

        JsonObject address1 = new JsonObject();
        address1.addProperty("no","180");
        address1.addProperty("street","3rd lane");
        address1.addProperty("city","panadura");

        JsonObject address2 = new JsonObject();
        address1.addProperty("no","24");
        address1.addProperty("street","3rd lane");
        address1.addProperty("city","panadura");

        JsonArray addresses = new JsonArray();
        addresses.add(address1);
        addresses.add(address2);

        customer.add("addresses",addresses);
        resp.setContentType("application/json");
        resp.getWriter().print(customer);
    }
}
