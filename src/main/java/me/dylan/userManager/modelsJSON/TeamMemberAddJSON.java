package me.dylan.userManager.modelsJSON;

public class TeamMemberAddJSON {
    private String username;
    private String currentID;
    private String teamname;
    private String membername;
    private long memberrights = -1;

    public String getUsername() { return username; }
    public String getCurrentID() { return currentID; }
    public String getTeamname() { return teamname; }
    public String getMembername() { return membername; }
    public long getMemberrights() { return memberrights; }

    public void setUsername(String username) { this.username = username; }
    public void setCurrentID(String currentID) { this.currentID = currentID; }
    public void setTeamname(String teamname) { this.teamname = teamname; }
    public void setMembername(String membername) { this.membername = membername; }
    public void setMemberrights(long memberrights) { this.memberrights = memberrights; }
}
