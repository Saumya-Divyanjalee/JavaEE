package lk.ijse.aad;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/data-formats")
public class DataFormatServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         //check the content type
        String contentType = req.getContentType();
        System.out.println("contentType: " + contentType);

        //query parameters
//        System.out.println(req.getParameter("id"));
//        System.out.println(req.getParameter("name"));

        //path variables


        //x-www-form-urlencoded
//        System.out.println(req.getParameter("id"));
//        System.out.println(req.getParameter("name"));

        //form-data
        System.out.println(req.getParameter("id"));
        System.out.println(req.getParameter("name"));

    }


}
