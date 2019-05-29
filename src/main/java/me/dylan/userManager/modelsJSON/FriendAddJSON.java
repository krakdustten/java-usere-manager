package me.dylan.userManager.modelsJSON;

public class FriendAddJSON {
    private String username;
    private String currentID;
    private String friendsname;

    public String getUsername() { return username; }
    public String getCurrentID() { return currentID; }
    public String getFriendsname() { return friendsname; }

    public void setUsername(String username) { this.username = username; }
    public void setCurrentID(String currentID) { this.currentID = currentID; }
    public void setFriendsname(String friendsname) { this.friendsname = friendsname; }
}
