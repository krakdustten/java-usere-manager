package me.dylan.userManager.db.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
@XmlRootElement
public class User implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="id")
    @NotNull
    private long id;

    @Column(name="name", unique = true)
    @NotNull
    private String name;

    @Column(name="email")
    @NotNull
    private String email;

    @Column(name="password")
    @Length(max = 128)
    @NotNull
    private String password;

    @Column(name="salt")
    @Length(max = 128)
    @NotNull
    private String salt;

    @Column(name="currentID")
    private String currentID;

    @Column(name="validUntilID")
    @Temporal(TemporalType.TIMESTAMP)
    @DefaultValue("0")
    private Date validUntilID;

    @Column(name="rights")
    @DefaultValue("0")
    private long rights;

    @Column(name="confirmed")
    @DefaultValue("false")
    private boolean confirmed;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    //@LazyCollection(LazyCollectionOption.FALSE)
    private List<Message> receivedMessages = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    //@LazyCollection(LazyCollectionOption.FALSE)
    private List<Message> senderMessages = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //@LazyCollection(LazyCollectionOption.FALSE)
    private List<TeamUser> teamUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL)
    //@LazyCollection(LazyCollectionOption.FALSE)
    private List<UserFriend> friendSend = new ArrayList<>();

    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL)
    //@LazyCollection(LazyCollectionOption.FALSE)
    private List<UserFriend> friendRecv = new ArrayList<>();

    public User(){ }

    public void addReceivedMessage(Message receivedMessage) {
        this.receivedMessages.add(receivedMessage);
        receivedMessage.setReceiver(this);
    }
    public void addSenderMessage(Message senderMessage) {
        this.senderMessages.add(senderMessage);
        senderMessage.setReceiver(this);
    }
    public void addTeamUser(TeamUser teamUser) {
        this.teamUsers.add(teamUser);
        teamUser.setUser(this);
    }
    public void addFriendSend(UserFriend friendSend) {
        this.friendSend.add(friendSend);
        friendSend.setUser1(this);
    }
    public void addFriendRecv(UserFriend friendRecv) {
        this.friendRecv.add(friendRecv);
        friendRecv.setUser2(this);
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getSalt() { return salt; }
    public String getCurrentID() { return currentID; }
    public Date getValidUntilID() { return validUntilID; }
    public long getRights() { return rights; }
    public boolean isConfirmed() { return confirmed; }
    public List<Message> getReceivedMessages() {
        if(receivedMessages != null) {
            if(!(receivedMessages instanceof PersistentBag))
                return receivedMessages;
        }
        return null;
    }
    public List<Message> getSenderMessages() {
        if(senderMessages != null)
            if(!(senderMessages instanceof PersistentBag))
                return senderMessages;
        return null;
    }
    public List<TeamUser> getTeamUsers() {
        if(teamUsers != null)
            if(!(teamUsers instanceof PersistentBag))
                return teamUsers;
        return null;
    }

    public List<UserFriend> getFriendSend() {
        if(friendSend != null)
            if(!(friendSend instanceof PersistentBag))
                return friendSend;
        return null;
    }

    public List<UserFriend> getFriendRecv() {
        if(friendRecv != null)
            if(!(friendRecv instanceof PersistentBag))
                return friendRecv;
        return null;
    }

    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setSalt(String salt) { this.salt = salt; }
    public void setCurrentID(String currentID) { this.currentID = currentID; }
    public void setValidUntilID(Date validUntilID) { this.validUntilID = validUntilID; }
    public void setRights(long rights) { this.rights = rights; }
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }

    public void setReceivedMessages(List<Message> receivedMessages) { this.receivedMessages = receivedMessages; }
    public void setSenderMessages(List<Message> senderMessages) { this.senderMessages = senderMessages; }
    public void setTeamUsers(List<TeamUser> teamUsers) { this.teamUsers = teamUsers; }
    public void setFriendSend(List<UserFriend> friendSend) { this.friendSend = friendSend; }
    public void setFriendRecv(List<UserFriend> friendRecv) { this.friendRecv = friendRecv; }
}
