/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author sheha
 */
@MultipartConfig
@WebServlet(name = "AddStory", urlPatterns = {"/AddStory"})
public class AddStory extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        JsonArray jsonarry = new JsonArray();

        System.out.println("gg");
        String cap = req.getParameter("cap").toString();
        String user = req.getParameter("user").toString();
        Part img = req.getPart("img");

        if (img != null) {
            String serverPath = req.getServletContext().getRealPath("");

            String avatarImagePath = serverPath + File.separator + "AvatarImages" + File.separator + user + ".png";
            System.out.println(avatarImagePath);
//            System.out.println(avatarImagePath);
//            File file = new File(avatarImagePath);
//            Files.copy(avatarImage.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

}
