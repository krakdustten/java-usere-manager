package me.dylan.userManager.modelsJSON;

public class UserRegisterJSON {
    String salt;
    String username;
    String password;
    String email;

    public String getSalt() { return salt; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }

    public void setSalt(String salt) { this.salt = salt; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
}
