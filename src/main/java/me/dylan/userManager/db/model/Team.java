package me.dylan.userManager.db.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="teams")
@XmlAccessorType(XmlAccessType.FIELD)
public class Team implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="id")
    @XmlTransient
    private long id;

    @Column(name="name", unique = true)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "team",cascade = {CascadeType.ALL}, fetch=FetchType.EAGER)
    private List<TeamUser> teamUsers = new ArrayList<>();

    public Team(){ }

    public long getId() { return id; }
    public String getName() { return name; }
    public List<TeamUser> getTeamUsers() { return teamUsers; }

    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }

    public void addTeamUser(TeamUser teamUser) {
        this.teamUsers.add(teamUser);
        teamUser.setTeam(this);
    }
}
