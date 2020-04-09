package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class carDetail extends AppCompatActivity {
    Toolbar myToolbar;
    TextView fuel,name,make,type,year,power,option;
    ListView opt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        TextView fuel = findViewById(R.id.carFuel);
        TextView name = findViewById(R.id.carName);
        TextView make = findViewById(R.id.carMake);
        TextView type = findViewById(R.id.carType);
        TextView year = findViewById(R.id.carYear);
        TextView power = findViewById(R.id.carPower);
        Intent intent = getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT)==true){
            String id = intent.getStringExtra(intent.EXTRA_TEXT);
            databaseHelper db = databaseHelper.getInstance(this);
            Car car = db.getCar(id);
            fuel.setText(car.fuel);
            name.setText(car.carName);
            make.setText(car.carMake);
            type.setText(car.carType);
            year.setText(car.year);
            power.setText(car.carPower);
            opt = findViewById(R.id.options);
            option = findViewById(R.id.option);
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.option,R.id.option,car.options){
              //https://stackoverflow.com/questions/7389997/how-to-make-an-item-in-a-list-view-non-clickable-in-android/17906722
                @Override
                public boolean isEnabled(int position){
                  return false;
              }
            };
            opt.setAdapter(adapter);
        }
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }
}
