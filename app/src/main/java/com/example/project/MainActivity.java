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
import java.util.List;

public class MainActivity extends AppCompatActivity implements carsAdapter.OnCarListener {
    List<String> carList= new ArrayList<String>(0);
    List<String> makeList= new ArrayList<String>(0);
    List<Integer> idList = new ArrayList<>(0);
    FloatingActionButton add;
    Toolbar myToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView cars = (RecyclerView) findViewById(R.id.cars);
        databaseHelper db = databaseHelper.getInstance(this);
        carList = db.getCarNames();
        makeList = db.getCarMakes();
        idList = db.getCarIds();
        add = findViewById(R.id.floatingActionButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openadd();
            }
        });
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        carsAdapter adapter = new carsAdapter(carList,makeList,this);
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
        intent.putExtra(Intent.EXTRA_TEXT, String.valueOf(idList.get(pos)));

        startActivity(intent);
    }
}