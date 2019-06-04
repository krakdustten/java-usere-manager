package me.dylan.userManager.modelsJSON;

public class UserRemoveJSON {
    private String currentID;
    private String username;
    private String usertoremove;

    public String getCurrentID() { return currentID; }
    public String getUsername() { return username; }
    public String getUsertoremove() { return usertoremove; }

    public void setCurrentID(String currentID) { this.currentID = currentID; }
    public void setUsername(String username) { this.username = username; }
    public void setUsertoremove(String usertoremove) { this.usertoremove = usertoremove; }
}
