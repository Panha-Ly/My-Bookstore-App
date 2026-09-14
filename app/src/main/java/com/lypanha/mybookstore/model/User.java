package com.lypanha.mybookstore.model;

public class User {
    public static final boolean LOGGED_IN = true;
    public static final boolean LOGGED_OUT = false;
    private final int id, imageId;
    private final String username, email, password;
    private boolean isLoginStatus;

    public User(int id, int imageId, String username, String email, String password, boolean isLoginStatus) {
        this.id = id;
        this.imageId = imageId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isLoginStatus = isLoginStatus;
    }

    public int getId() {
        return id;
    }

    public int getImageId() {
        return imageId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoginStatus() {
        return isLoginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        isLoginStatus = loginStatus;
    }
}
