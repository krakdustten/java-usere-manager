package me.dylan.userManager.util;

import java.util.HashMap;
import java.util.Map;

public enum MapError {
    USER_NOT_FOUND (521, "User not found."),
    USER_ALREADY_EXISTS (522, "User already exists."),
    JSON_STRUCTURE_WRONG (100, "Json structure wrong.");


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

    @Override
    public String toString() {
        return"{ \"error\": \"" + error + "\", \"errorName\": \"" + errorName + "\" }";
    }
}
