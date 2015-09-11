package com.KMoskvitin.myassistent.app;

public class User {

    int type;
    String message;
    String time;
    String image;

    public User(int type, String message, String time, String image) {
        this.type = type;
        this.message = message;
        this.time = time;
        this.image = image;
    }

    public User(int typeSystem, String sysMessage, String timeString) {

    }

    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "User{" +
                "type=" + type +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
