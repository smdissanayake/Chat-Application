/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.User;
import entity.status;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.HibernateUtil;
import model.Validations;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sheha
 */
@WebServlet(name = "signUp", urlPatterns = {"/signUp"})
public class signUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("gg");
//        Gson gson = new Gson();
//        JsonObject responseJson = gson.fromJson(request.getReader(), JsonObject.class);
//        JsonObject responseJson = gson.fromJson(request.getReader(), JsonObject.class);

        Gson gson = new Gson();
        JsonObject responseJson = gson.fromJson(request.getReader(), JsonObject.class);

        String mobile = responseJson.get("Mobile").getAsString();
        String pw = responseJson.get("pw").getAsString();
        System.out.println(mobile);

        if (mobile.isEmpty()) {
            responseJson.addProperty("msg", "Place Enter Your Mobile Number ");
        } else if (!Validations.isMobileNumberValid(mobile)) {
            responseJson.addProperty("msg", "Invalid Mobile Number");
        } else if (pw.isEmpty()) {
            responseJson.addProperty("msg", "Please Enter Password");
        } else if (!Validations.isPasswordValid(pw)) {
            responseJson.addProperty("msg", "Invalid Password");
        } else {

            Session session = HibernateUtil.getSessionFactory().openSession();

            Criteria c1 = session.createCriteria(User.class);
            c1.add(Restrictions.eq("mobile", mobile));
            c1.add(Restrictions.eq("password", pw));

            if (!c1.list().isEmpty()) {
                User u = (User) c1.uniqueResult();
                responseJson.add("user", gson.toJsonTree(u));
                responseJson.addProperty("sucsess", true);
                responseJson.addProperty("msg", "user Registered Succsessfuly");
                System.out.println(responseJson.get("user"));

            } else {
                responseJson.addProperty("msg", "user Not Found");
            }
            response.setContentType("application/json");
            session.close();
        }

        response.getWriter().write(gson.toJson(responseJson));

    }

}
