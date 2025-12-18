package lk.ijse.aad;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = "/data-formats/*")
//path variables
@MultipartConfig
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
//        System.out.println(req.getParameter("id"));
//        System.out.println(req.getParameter("name"));
//        Part image = req.getPart("image");
//        System.out.println(image.getSubmittedFileName());
////create a directory
//        File upload = new File
//                ("C:\\Users\\saumy\\IdeaProjects\\AAD-main\\JavaEE\\JavaEE-Theory\\Day04-Application02\\src\\main\\resources\\images");
//        if (!upload.exists()) {
//            upload.mkdir();
//        }
//        //save the file
//        String fullPath = upload.getAbsolutePath() + File.separator +image.getSubmittedFileName();
//        image.write(fullPath);


        //path variable
        System.out.println(req.getPathInfo());
        System.out.println(req.getPathInfo().substring(1));



    }


}
