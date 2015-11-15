package com.example.dannyliu.watchewupto;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button bLogin;
    EditText etEmail, etPassword;
    TextView tvRegisterLink;
    UserLocalStore userLocalStore;

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bLogin:
                //when a user logs in, we need to store the information about that user and also tell the database that a user is logged in
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                User user = new User(email, password);

                authenticate(user);
                break;

            case R.id.tvRegisterLink:

                startActivity(new Intent(this, Register.class));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        userLocalStore = new UserLocalStore(this);

        tvRegisterLink.setOnClickListener(this);
        bLogin.setOnClickListener(this);

    }

    private void authenticate(User user){
        ServerRequest serverRequests = new ServerRequest(this);
        //this is actually so cool!
        //Java here is imitating callback functions in javascript with the user of an interface and the concept of abstract functions
        serverRequests.fetchUserDataInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null) {
                    showErrorMessage();
                } else {
                    logUserIn(returnedUser);
                }
            }
        });
    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Incorrect user details!");
        dialogBuilder.setPositiveButton("Ok", null);
    }

    private void logUserIn(User returnedUser){
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);

        startActivity(new Intent(Login.this, MainActivity.class));
    }

}
