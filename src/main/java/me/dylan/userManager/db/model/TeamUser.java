package me.dylan.userManager.db.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@Entity
@Table(name="team_users")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamUser implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="id")
    @XmlTransient
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public TeamUser() { }

    public long getId() { return id; }
    public User getUser() { return user; }
    public Team getTeam() { return team; }

    public void setId(long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setTeam(Team team) { this.team = team; }
}
