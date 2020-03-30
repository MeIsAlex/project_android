package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements carsAdapter.OnCarListener {
    ArrayList<String> carList= new ArrayList<String>(1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView cars = (RecyclerView) findViewById(R.id.cars);
        for(int i=0;i<20;i++) {
            carList.add("test");
        }
        carsAdapter adapter = new carsAdapter(carList,this);
        cars.setAdapter(adapter);
        cars.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onCarClick(int pos) {
        Intent intent = new Intent(this, carDetail.class);
        intent.putExtra(Intent.EXTRA_TEXT, String.valueOf(pos));

        startActivity(intent);
    }
}