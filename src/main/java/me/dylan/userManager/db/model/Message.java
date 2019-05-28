package me.dylan.userManager.db.model;

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
    @Id
    @GeneratedValue
    @Column(name="id")
    @NotNull
    private long id;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    public Message(){}

    public long getId() { return id; }
    public User getReceiver() { return receiver; }

    public void setId(long id) { this.id = id; }
    public void setReceiver(User receiver){
        this.receiver = receiver;
    }
}
