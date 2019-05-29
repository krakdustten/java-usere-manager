package me.dylan.userManager.db.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@Entity
@Table(name="messages")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {

    @Id
    @GeneratedValue
    @Column(name="id")
    @XmlTransient
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

    public long getId() { return id; }
    public User getReceiver() { return receiver; }
    public User getSender() { return sender; }
    public String getMessage() { return message; }
    public Date getSendTime() { return sendTime; }
    public boolean isReceiverRead() { return receiverRead; }

    public void setId(long id) { this.id = id; }
    public void setReceiver(User receiver){ this.receiver = receiver; }
    public void setSender(User sender) { this.sender = sender; }
    public void setMessage(String message) { this.message = message; }
    public void setSendTime(Date sendTime) { this.sendTime = sendTime; }
    public void setReceiverRead(boolean receiverRead) { this.receiverRead = receiverRead; }
}
