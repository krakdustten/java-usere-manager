package me.dylan.userManager.modelsJSON;

public class UserRoleJSON {
    private String currentID;
    private String username;
    private String setname;
    private long setrights = -1;

    public String getCurrentID() { return currentID; }
    public String getUsername() { return username; }
    public String getSetname() { return setname; }
    public long getSetrights() { return setrights; }

    public void setCurrentID(String currentID) { this.currentID = currentID; }
    public void setUsername(String username) { this.username = username; }
    public void setSetname(String setname) { this.setname = setname; }
    public void setSetrights(long setrights) { this.setrights = setrights; }
}
