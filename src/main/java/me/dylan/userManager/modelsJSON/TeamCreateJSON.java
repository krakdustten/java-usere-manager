package me.dylan.userManager.modelsJSON;

public class TeamCreateJSON {
    private String username;
    private String currentID;
    private String teamname;

    public String getUsername() { return username; }
    public String getCurrentID() { return currentID; }
    public String getTeamname() { return teamname; }

    public void setUsername(String username) { this.username = username; }
    public void setCurrentID(String currentID) { this.currentID = currentID; }
    public void setTeamname(String teamname) { this.teamname = teamname; }
}
