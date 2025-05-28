/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.Chat;
import entity.User;
import entity.chat_status;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import java.lang.NumberFormatException;
import org.hibernate.criterion.Order;

/**
 *
 * @author sheha
 */
@WebServlet(name = "Loadchat", urlPatterns = {"/Loadchat"})
public class Loadchat extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Gson gson = new Gson();

        String log_user = req.getParameter("Luser");
        String other_user = req.getParameter("Ouser");

        User Log_u = (User) session.get(User.class, Integer.parseInt(log_user));
        User other_u = (User) session.get(User.class, Integer.parseInt(other_user));

        chat_status chatStatus = (chat_status) session.get(chat_status.class, 1);

        Criteria c1 = session.createCriteria(Chat.class);
        c1.add(
                Restrictions.or(
                        Restrictions.and(Restrictions.eq("to_user", other_u), Restrictions.eq("from_user", Log_u)),
                        Restrictions.and(Restrictions.eq("from_user", other_u), Restrictions.eq("to_user", Log_u))
                )
        );
        c1.addOrder(Order.desc("DateT"));
        List<Chat> chat_list = c1.list();     
//        create chat Arry
        JsonArray chatArray = new JsonArray();

//        createDate Formate
        SimpleDateFormat dd = new SimpleDateFormat("hh:mm a");

        for (Chat chat : chat_list) {
//create chat object
            JsonObject chatObj = new JsonObject();
            chatObj.addProperty("msg", chat.getMessage());
            chatObj.addProperty("date", dd.format(chat.getDateT()));
//other user chat  
//            System.out.println(other_u.getId());

            if (chat.getFrom_user().getId() == other_u.getId()) {
//                addside other
                chatObj.addProperty("side", "left");
                if (chat.getChat_status().getId() == 2) {
                    chat.setChat_status(chatStatus);
                    session.update(chat);
                }
            } else {

                chatObj.addProperty("side", "rigth");
                chatObj.addProperty("seen", chat.getChat_status().getId());//1=seen 2=noseen

            }

            chatArray.add(chatObj);
        }
        session.beginTransaction().commit();
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(chatArray));
    }

}
