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
        SharedPreferences.Editor spEditor = userLocalDatabases.edit();
        spEditor.putString("name", user.name);
        spEditor.putString("email", user.email);
        spEditor.putString("password", user.password);
        spEditor.commit();

    }
}
