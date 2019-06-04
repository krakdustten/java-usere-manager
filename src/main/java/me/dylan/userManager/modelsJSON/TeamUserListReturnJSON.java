package me.dylan.userManager.modelsJSON;

import java.util.List;

public class TeamUserListReturnJSON {
    private long id;
    private List<TeamUsersReturnJSON> userlist;

    public long getId() { return id; }
    public List<TeamUsersReturnJSON> getUserlist() { return userlist; }

    public void setId(long id) { this.id = id; }
    public void setUserlist(List<TeamUsersReturnJSON> userlist) { this.userlist = userlist; }
}
