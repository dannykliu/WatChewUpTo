package com.example.dannyliu.watchewupto;

/**
 * Created by dannyliu on 11/14/15.
 */
public class User {

    String name, email, password;

    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }
}
