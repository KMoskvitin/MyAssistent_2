package com.KMoskvitin.myassistent.app;

public class User {

    int type;
    String message;
    String chatTime;
    String imageId;

    public User(int type, String message, String chatTime, String imageId) {
        this.type = type;
        this.message = message;
        this.chatTime = chatTime;
        this.imageId = imageId;
    }

    public User(int type, String message, String chatTime) {
        this.type = type;
        this.message = message;
        this.chatTime = chatTime;
        this.imageId = "";
    }

    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getChatTime() {
        return chatTime;
    }

    public String getImageId() {
        return imageId;
    }

    @Override
    public String toString() {
        return "User{" +
                "type=" + type +
                ", message='" + message + '\'' +
                ", chatTime='" + chatTime + '\'' +
                '}';
    }
}
