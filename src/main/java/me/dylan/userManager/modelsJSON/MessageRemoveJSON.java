package me.dylan.userManager.modelsJSON;

public class MessageRemoveJSON {
    private String username;
    private String currentID;
    private long messageID = -1;

    public String getUsername() { return username; }
    public String getCurrentID() { return currentID; }
    public long getMessageID() { return messageID; }

    public void setUsername(String username) { this.username = username; }
    public void setCurrentID(String currentID) { this.currentID = currentID; }
    public void setMessageID(long messageID) { this.messageID = messageID; }
}
