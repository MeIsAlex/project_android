package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements carsAdapter.OnCarListener {
    ArrayList<String> carList= new ArrayList<String>(1);
    FloatingActionButton add;
    Toolbar myToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView cars = (RecyclerView) findViewById(R.id.cars);
        for(int i=0;i<20;i++) {
            carList.add("test");
        }
        add= findViewById(R.id.floatingActionButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openadd();
            }
        });
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        carsAdapter adapter = new carsAdapter(carList,this);
        cars.setAdapter(adapter);
        cars.setLayoutManager(new LinearLayoutManager(this));
    }

    private void openadd() {
        Intent intent = new Intent(this,createCar.class);
        startActivity(intent);
    }

    @Override
    public void onCarClick(int pos) {
        Intent intent = new Intent(this, carDetail.class);
        intent.putExtra(Intent.EXTRA_TEXT, String.valueOf(pos));

        startActivity(intent);
    }
}