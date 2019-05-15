package com.johnfreier.mypassword.domain;

public class Item implements Comparable<Item> {

    private String title = "";
    private String username = "";
    private String password = "";
    private String note = "";
    private String url = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public int compareTo(Item another) {
        return this.getTitle().compareTo(another.getTitle());
    }
}
