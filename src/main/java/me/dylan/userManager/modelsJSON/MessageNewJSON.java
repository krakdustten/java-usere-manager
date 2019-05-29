package me.dylan.userManager.modelsJSON;

public class MessageNewJSON {
    private String username;
    private String currentID;
    private String receivername;
    private String message;

    public String getUsername() { return username; }
    public String getCurrentID() { return currentID; }
    public String getReceivername() { return receivername; }
    public String getMessage() { return message; }

    public void setUsername(String username) { this.username = username; }
    public void setCurrentID(String currentID) { this.currentID = currentID; }
    public void setReceivername(String receivername) { this.receivername = receivername; }
    public void setMessage(String message) { this.message = message; }
}
