package me.dylan.userManager.db.model;

import me.dylan.userManager.db.dao.UserDAO;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


@Entity
@Table(name="messages")
@XmlRootElement
public class Message {

    @Transient
    private UserDAO userDAO = UserDAO.get();

    @Transient
    private long sender_id;

    @Transient
    private long receiver_id;


    @Id
    @GeneratedValue
    @Column(name="id")
    @NotNull
    private long id;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;


    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;


    @Column(name="message")
    @Length(max = 1024)
    @NotNull
    private String message;

    @Column(name="sendTime")
    @Temporal(TemporalType.TIMESTAMP)
    @DefaultValue("0")
    private Date sendTime;

    @Column(name="receiverRead")
    @DefaultValue("false")
    private boolean receiverRead;

    public Message(){}

    public Message(long id, String message, Date sendTime, boolean receiverRead) {
        this.id = id;
        this.message = message;
        this.sendTime = sendTime;
        this.receiverRead = receiverRead;
    }

    public User getReceiver() {
        if(receiver != null){
            if(receiver.getId() == receiver_id)
                return receiver;
        }
        receiver = userDAO.get(receiver_id);
        return receiver;
    }

    public User getSender() {
        if(sender != null){
            if(sender.getId() == sender_id)
                return sender;
        }
        sender = userDAO.get(sender_id);
        return sender;
    }
    public long getId() { return id; }
    public String getMessage() { return message; }
    public Date getSendTime() { return sendTime; }
    public boolean isReceiverRead() { return receiverRead; }

    public void setId(long id) { this.id = id; }
    public void setReceiver(User receiver) {
        this.receiver = receiver;
        receiver_id = receiver.getId();
    }
    public void setReceiver(long receiver_id){ this.receiver_id = receiver_id; }
    public void setSender(User sender) {
        this.sender = sender;
        sender_id = sender.getId();
    }
    public void setSender(long sender_id) {
        this.sender_id = sender_id;
    }
    public void setMessage(String message) { this.message = message; }
    public void setSendTime(Date sendTime) { this.sendTime = sendTime; }
    public void setReceiverRead(boolean receiverRead) { this.receiverRead = receiverRead; }
}
