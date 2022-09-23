package com.example.demo.longpolling.event;

import com.example.demo.notify.Event;

public class LoginEvent extends Event {

    private String username;

    public LoginEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
