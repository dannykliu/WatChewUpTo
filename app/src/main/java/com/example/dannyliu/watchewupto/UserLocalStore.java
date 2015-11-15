package com.example.dannyliu.watchewupto;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dannyliu on 11/14/15.
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabases;

    //UserLocalStore is a java class so it cannot instantiate SharedPreferences
    //With this solution, when you create a UserLocalStore, it needs to be given a context from the activity which is using it
    //The context will then get the SharedPreferences
    public UserLocalStore(Context context){
        userLocalDatabases = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user){
        //.edit() returns an Editor
        SharedPreferences.Editor spEditor = userLocalDatabases.edit();
        spEditor.putString("name", user.name);
        spEditor.putString("email", user.email);
        spEditor.putString("password", user.password);
        spEditor.commit();

    }

    public User getLoggedInUser(){
        //fetch data from the database
        String name = userLocalDatabases.getString("name", "");
        String password = userLocalDatabases.getString("password", "");
        String email = userLocalDatabases.getString("email", "");

        //instantiate our user and return it
        User storedUser = new User(name, email, password);
        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabases.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        //if the user is logged in, return true
        if(userLocalDatabases.getBoolean("loggedIn", false)){
            return true;
        } else {
            return false;
        }
    }
    public void clearUserData(){
        //This makes a local copy of the sharedpref.
        SharedPreferences.Editor spEditor = userLocalDatabases.edit();
        //Make changes on the sharepref
        spEditor.clear();
        //commit these changes
        spEditor.commit();
    }
}
