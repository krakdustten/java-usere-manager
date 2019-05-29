package me.dylan.userManager.modelsJSON;

import java.util.Date;

public class FriendReturnJSON {
    private long id;
    private String name;
    private Date startTime;
    private boolean accepted;
    private boolean askedByUser;

    public long getId() { return id; }
    public String getName() { return name; }
    public Date getStartTime() { return startTime; }
    public boolean isAccepted() { return accepted; }
    public boolean isAskedByUser() { return askedByUser; }

    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }
    public void setAccepted(boolean accepted) { this.accepted = accepted; }
    public void setAskedByUser(boolean askedByUser) { this.askedByUser = askedByUser; }
}
