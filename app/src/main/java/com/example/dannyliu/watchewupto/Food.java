package com.example.dannyliu.watchewupto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Food extends AppCompatActivity implements View.OnClickListener {

    Button btnProfile;
    Button btnSearch;
    Button btnHome;
    List<String> dailyFoodsOffered = new ArrayList<String>();
    ListView list = (ListView) findViewById(R.id.foodList);
    int listSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        btnProfile = (Button) findViewById(R.id.btnProfile);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnHome = (Button) findViewById(R.id.btnHome);
        //Temporary text
        dailyFoodsOffered.add("text");

        //Getting food names and adding them to listArray
        for (int i = 0; i < listSize; i++) {
            dailyFoodsOffered.add("text");
        }
        //Setting arrayList into the listView
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                dailyFoodsOffered);
        list.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food, menu);
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
            case (R.id.btnSearch):
                startActivity(new Intent(this, Search.class));
                break;
            case (R.id.btnHome):
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
