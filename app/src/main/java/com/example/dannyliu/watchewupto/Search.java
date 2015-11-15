package com.example.dannyliu.watchewupto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Search extends AppCompatActivity implements View.OnClickListener  {

    //Creating buttons
    Button btnProfile;
    Button btnFood;
    Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initializing buttons
        btnProfile = (Button) findViewById(R.id.btnProfile);
        btnFood = (Button) findViewById(R.id.btnDailyFoods);
        btnHome = (Button) findViewById(R.id.btnHome);
        setContentView(R.layout.activity_search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btnProfile):
                startActivity(new Intent(this, Profile.class));
                break;
            case (R.id.btnDailyFoods):
                startActivity(new Intent(this, Food.class));
                break;
            case (R.id.btnHome):
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
