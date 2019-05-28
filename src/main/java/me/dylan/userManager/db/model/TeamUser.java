package me.dylan.userManager.db.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name="team_users")
@XmlRootElement
public class TeamUser implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="id")
    @NotNull
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
