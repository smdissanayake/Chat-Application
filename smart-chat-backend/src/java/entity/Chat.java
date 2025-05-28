/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author sheha
 */
@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User from_user;

    @ManyToOne
    @JoinColumn(name = "to_user_id1")
    private User to_user;

    @Column(name = "msg", nullable = false)
    private String message;

    @Column(name = "DateT", nullable = false)
   @Temporal(TemporalType.TIMESTAMP) 
    private Date DateT;

    @ManyToOne
    @JoinColumn(name = "chat_status_id")
    private chat_status chat_status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getFrom_user() {
        return from_user;
    }

    public void setFrom_user(User from_user) {
        this.from_user = from_user;
    }

    public User getTo_user() {
        return to_user;
    }

    public void setTo_user(User to_user) {
        this.to_user = to_user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public chat_status getChat_status() {
        return chat_status;
    }

    public void setChat_status(chat_status chat_status) {
        this.chat_status = chat_status;
    }

    public Date getDateT() {
        return DateT;
    }

    public void setDateT(Date DateT) {
        this.DateT = DateT;
    }



}
