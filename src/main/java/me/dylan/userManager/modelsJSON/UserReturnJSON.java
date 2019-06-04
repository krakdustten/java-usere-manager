package me.dylan.userManager.modelsJSON;

import java.util.Date;

public class UserReturnJSON {
    private long id;
    private String name;
    private String email;
    private String currentID;
    private Date validUntilID;
    private long rights;

    public long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCurrentID() { return currentID; }
    public Date getValidUntilID() { return validUntilID; }
    public long getRights() { return rights; }

    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setCurrentID(String currentID) { this.currentID = currentID; }
    public void setValidUntilID(Date validUntilID) { this.validUntilID = validUntilID; }
    public void setRights(long rights) { this.rights = rights; }
}
