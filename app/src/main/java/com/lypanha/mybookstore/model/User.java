package com.lypanha.mybookstore.model;

public class User {
    public static final boolean LOGGED_IN = true;
    public static final boolean LOGGED_OUT = false;
    private int id, imageId;
    private String username, email, password;
    private boolean loginStatus;

    public User(int id, int imageId, String username, String email, String password, boolean loginStatus) {
        this.id = id;
        this.imageId = imageId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.loginStatus = loginStatus;
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
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }
}
