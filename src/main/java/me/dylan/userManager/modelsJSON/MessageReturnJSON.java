package me.dylan.userManager.modelsJSON;

import java.util.Date;

public class MessageReturnJSON {
    private long id;
    private String sender;
    private String receiver;
    private String message;
    private Date sendTime;
    private boolean receiverRead;

    public long getId() { return id; }
    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public String getMessage() { return message; }
    public Date getSendTime() { return sendTime; }
    public boolean isReceiverRead() { return receiverRead; }

    public void setId(long id) { this.id = id; }
    public void setSender(String sender) { this.sender = sender; }
    public void setReceiver(String receiver) { this.receiver = receiver; }
    public void setMessage(String message) { this.message = message; }
    public void setSendTime(Date sendTime) { this.sendTime = sendTime; }
    public void setReceiverRead(boolean receiverRead) { this.receiverRead = receiverRead; }
}
