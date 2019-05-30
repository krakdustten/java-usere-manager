package me.dylan.userManager.util;

import javax.ws.rs.core.Response;

public enum MapError {
    USER_NOT_FOUND (521, "User not found."),
    USER_ALREADY_EXISTS (522, "User already exists."),
    USER_RIGHTS_TO_LOW (551, "User rights where too low."),
    JSON_STRUCTURE_WRONG (100, "Json structure wrong."),
    USERS_ALREADY_FRIENDS (622, "Users already friends."),
    USERS_NOT_FRIENDS (623, "Users not friends."),
    MESSAGE_NOT_FOUND (721, "Message not found."),
    TEAM_NOT_FOUND (821, "Team not found."),
    TEAM_ALREADY_EXISTS (822, "Team already exists."),
    USER_NOT_FOUND_IN_TEAM (823, "User was not part of team."),
    USER_TEAM_RIGHTS_TOO_LOW (851, "User team rights where too low."),
    USER_ALREADT_IN_TEAM (823, "User already in team.");

    private int error;
    private String errorName;

    MapError(int id, String name) {
        this.error = id;
        this.errorName = name;
    }

    public int getId() {
        return error;
    }
    public String getName() {
        return errorName;
    }
    public Response getError(){
        return Response.status(Response.Status.OK).entity(this.toString()).build();
    }

    @Override
    public String toString() {
        return"{ \"error\": " + error + ", \"errorName\": \"" + errorName + "\" }";
    }
}
