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
import entity.status;
import java.io.File;
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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sheha
 */
@WebServlet(name = "LoadHomeData", urlPatterns = {"/LoadHomeData"})
public class LoadHomeData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user_Id = req.getParameter("uid");
        System.out.println(user_Id);
        Gson gson = new Gson();
        JsonObject responseJson = new JsonObject();

        Session session = HibernateUtil.getSessionFactory().openSession();
        responseJson.addProperty("status", false);
        responseJson.addProperty("message", "Unable to process your request");

        User user = (User) session.get(User.class, Integer.parseInt(user_Id));

        // get user status = 1 (online)
        status user_Status = (status) session.get(status.class, 1);

        // update user status
        user.setStatus(user_Status);
        session.update(user);

        // get other users
        Criteria criterial = session.createCriteria(User.class);
        criterial.add(Restrictions.ne("id", user.getId()));

        List<User> otherUserList = criterial.list();

        // get other user one by one
        JsonArray jsonChatArray = new JsonArray();

        // Use Projections to get the row count
        for (User otherUser : otherUserList) {
//                get last conversation 
            Criteria criteria2 = session.createCriteria(Chat.class);
            criteria2.add(
                    Restrictions.or(
                            Restrictions.and(
                                    Restrictions.eq("from_user", user),
                                    Restrictions.eq("to_user", otherUser)
                            ),
                            Restrictions.and(
                                    Restrictions.eq("from_user", otherUser),
                                    Restrictions.eq("to_user", user)
                            )
                    )
            );

            criteria2.addOrder(Order.desc("id"));
//            criteria2.setMaxResults(1);
            JsonObject jsonChatItem = new JsonObject();
            jsonChatItem.addProperty("other_user_id", otherUser.getId());
            jsonChatItem.addProperty("other_user_mobile", otherUser.getMobile());
            jsonChatItem.addProperty("other_user_name", otherUser.getFirst_name() + " " + otherUser.getLast_name());
            jsonChatItem.addProperty("other_user_status", otherUser.getStatus().getId());     //1=online, 2=offline

//check avatar image
            String serverPath = req.getServletContext().getRealPath("");
            String otherUserAvatarImagePath = serverPath + File.separator + "AvatarImages" + File.separator + otherUser.getMobile() + ".png";
            File otherUserAvatarImageFile = new File(otherUserAvatarImagePath);

            if (otherUserAvatarImageFile.exists()) {
                //avatar image found
                jsonChatItem.addProperty("avatar_image_found", true);
            } else {
                //avatar image not found
                jsonChatItem.addProperty("avatar_image_found", false);
                jsonChatItem.addProperty("other_user_avatar_letters", otherUser.getFirst_name().charAt(0) + "" + otherUser.getLast_name().charAt(0));
            }

//get chat list
            List<Chat> dbChatList = criteria2.list();

            Criteria criteria3 = session.createCriteria(Chat.class);
            criteria3.add(
//                    Restrictions.or(
//                            Restrictions.and(
//                                    Restrictions.eq("from_user", user),
//                                    Restrictions.eq("to_user", otherUser)
//                            ),
                            Restrictions.and(
                                    Restrictions.eq("from_user", otherUser),
                                    Restrictions.eq("to_user", user)
                            )
//                    )
            );
            chat_status ch = (chat_status) session.get(chat_status.class, 2);
            criteria3.addOrder(Order.desc("id"));
            criteria3.add(Restrictions.eq("chat_status", ch));
            List<Chat> ChatList = criteria3.list();

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy, MMM dd hh:mm a");
            System.out.println(ChatList.size());
            if (dbChatList.isEmpty()) {
                //no chat found
                jsonChatItem.addProperty("message", "Let's Start New Conversation");
                jsonChatItem.addProperty("dateTime", dateFormat.format(user.getRegistationD()));
                jsonChatItem.addProperty("chat_status_id", 1); //1=seen, 2=unseen

            } else {
                //found last chat
                jsonChatItem.addProperty("message", dbChatList.get(0).getMessage());
                jsonChatItem.addProperty("dateTime", dateFormat.format(dbChatList.get(0).getDateT()));
                jsonChatItem.addProperty("chat_status_id", dbChatList.get(0).getChat_status().getId()); //1=seen, 2=unseen
                jsonChatItem.addProperty("s_Seen", ChatList.size());
            }

//add to chat array
            jsonChatArray.add(jsonChatItem);
        }

//        } catch (Exception e) {
        // Handle the exception
//        }
        System.out.println(jsonChatArray);
        responseJson.addProperty("status", true);
        responseJson.addProperty("msg", "succsess");
        responseJson.add("otherUser", gson.toJsonTree(jsonChatArray));
        resp.getWriter().write(gson.toJson(responseJson));
        session.beginTransaction().commit();
    }

}
