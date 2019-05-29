package me.dylan.userManager.util;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public enum MapError {
    USER_NOT_FOUND (521, "User not found."),
    USER_ALREADY_EXISTS (522, "User already exists."),
    JSON_STRUCTURE_WRONG (100, "Json structure wrong."),
    USERS_ALREADY_FRIENDS (622, "Users already friends."),
    USERS_NOT_FRIENDS (623, "Users not friends.");


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
        return"{ \"error\": \"" + error + "\", \"errorName\": \"" + errorName + "\" }";
    }
}