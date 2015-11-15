package com.example.dannyliu.watchewupto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    Button btnSearch;
    Button btnFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnFood = (Button) findViewById(R.id.btnDailyFoods);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
            case (R.id.btnDailyFoods):
                startActivity(new Intent(this, Food.class));
                break;
            case (R.id.btnSearch):
                startActivity(new Intent(this, Search.class));
                break;
        }
    }
}
