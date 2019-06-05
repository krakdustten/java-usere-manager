package me.dylan.userManager.modelsJSON;

public class UserSmallReturnJSON {
    private long id;
    private String name;
    private String email;

    public long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}
