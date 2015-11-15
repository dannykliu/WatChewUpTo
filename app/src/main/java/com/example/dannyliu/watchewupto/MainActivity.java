package com.example.dannyliu.watchewupto;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button bLogout, bQuery;
    EditText etName, etEmail, etQuery;
    UserLocalStore userLocalStore;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        bLogout = (Button) findViewById(R.id.bLogout);
        etQuery = (EditText) findViewById(R.id.etQuery);
        bQuery = (Button) findViewById(R.id.bQuery);


//
//        etQuery.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if(event.getAction() == KeyEvent.KEYCODE_ENTER){
//                    query = etQuery.getText().toString();
//                    sendQuery(query);
//                    return true;
//                }
//                return false;
//            }
//        });

        //'this' is the context
        userLocalStore = new UserLocalStore(this);

        bLogout.setOnClickListener(this);
        bQuery.setOnClickListener(this);


    }

    private void sendQuery(String query){
        Log.i("debug", "SERVER REQUEST CREATED ");

        ServerRequest serverRequests = new ServerRequest(this);
        Log.i("debug", "STARTED CALLBACK ");
        serverRequests.sendQueryInBackground(query, new GetQueryCallback() {
            @Override
            public void done(ArrayList<FoodItem> queryFood) {
                Log.i("debug", "COMPLETED CALLBACK ");

                //load the next page
                //query is the arraylist of fooditems
                if (queryFood == null) {
                    showErrorMessage();
                    Log.i("debug", "SUM TING REALLY WONG ");

                } else {
                    Intent i = new Intent(MainActivity.this, Food.class);
                    i.putParcelableArrayListExtra("query", queryFood);
                    Log.i("debug", queryFood.size()+"");
                    i.putExtra("query", queryFood);
                    startActivity(i);
                }
            }
        });
    }


    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setMessage("Incorrect user details!");
        dialogBuilder.setPositiveButton("Ok", null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //When the main activity starts, our app will attempt to authenticate the user
    //If the user is logged on, we then display the details using displayUserDetails()

    @Override
    protected void onStart() {
        super.onStart();

        if(authenticate()){
            displayUserDetails();
        } else {
            startActivity(new Intent(this, Login.class));
        }
    }

    private boolean authenticate(){
       return userLocalStore.getUserLoggedIn();
    }

    private void displayUserDetails(){
        //get the user that's logged in
        User user = userLocalStore.getLoggedInUser();
        etEmail.setText(user.email);
        etName.setText(user.name);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.bLogout):

                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                startActivity(new Intent(this, Login.class));
                break;

            case (R.id.bQuery):
                query = etQuery.getText().toString();
                Log.i("debug", "SEND QUERY CALL");
                sendQuery(query);
                Log.i("debug", "DONE QUERY CALL");

                break;
        }
    }


}
