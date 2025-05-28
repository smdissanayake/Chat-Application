/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.User;
import entity.status;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import model.Validations;
import org.hibernate.Session;

/**
 *
 * @author sheha
 */
@WebServlet(name = "signIn", urlPatterns = {"/signIn"})
@MultipartConfig
public class signIn extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("ggg");
        Gson gson = new Gson();
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("message", "Server:Hello!");

        String mobile = request.getParameter("mobile");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String pw = request.getParameter("pw");
        Part avatarImage = request.getPart("img");
        System.out.println(mobile);
        if (mobile.isEmpty()) {
            responseJson.addProperty("msg", "Place Enter Your Mobile Number ");
        } else if (!Validations.isMobileNumberValid(mobile)) {
            responseJson.addProperty("msg", "Invalid Mobile Number");
        } else if (fname.isEmpty()) {
            responseJson.addProperty("msg", "Please Enter First Name");
        } else if (lname.isEmpty()) {
            responseJson.addProperty("msg", "Invalid Mobile Number");
        } else if (pw.isEmpty()) {
            responseJson.addProperty("msg", "Please Enter Password");
        } else if (!Validations.isPasswordValid(pw)) {
            responseJson.addProperty("msg", "Invalid Password");
        } else {

        Session session = HibernateUtil.getSessionFactory().openSession();

            User u = new User();
            u.setMobile(mobile);
            u.setFirst_name(fname);
            u.setLast_name(lname);
            u.setPassword(pw);
            u.setRegistationD(new Date());

            status ust = (status) session.get(status.class, 2);
            u.setStatus(ust);

            if (avatarImage != null) {
                String serverPath = request.getServletContext().getRealPath("");
                String avatarImagePath = serverPath + File.separator + "AvatarImages" + File.separator + mobile + ".png";
                System.out.println(avatarImagePath);
                File file = new File(avatarImagePath);
                Files.copy(avatarImage.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            responseJson.addProperty("sucsess", true);
            responseJson.addProperty("msg", "user Registered Succsessfuly");
            session.save(u);
            session.beginTransaction().commit();
            session.close();
        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseJson));

    }

}
