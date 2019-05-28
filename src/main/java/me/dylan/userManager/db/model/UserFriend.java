package me.dylan.userManager.db.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="user_friends")
@XmlRootElement
public class UserFriend implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="id")
    @NotNull
    private long id;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private User user2;

    @Column(name="validUntilID")
    @Temporal(TemporalType.TIMESTAMP)
    @DefaultValue("0")
    private Date validUntilID;

    @Column(name="confirmed")
    @DefaultValue("false")
    private boolean confirmed;

    public UserFriend() { }

    public long getId() { return id; }
    public User getUser1() { return user1; }
    public User getUser2() { return user2; }
    public Date getValidUntilID() { return validUntilID; }
    public boolean isConfirmed() { return confirmed; }

    public void setId(long id) { this.id = id; }
    public void setUser1(User user1) { this.user1 = user1; }
    public void setUser2(User user2) { this.user2 = user2; }
    public void setValidUntilID(Date validUntilID) { this.validUntilID = validUntilID; }
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }
}
