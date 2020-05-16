package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class carDetail extends AppCompatActivity {
    Toolbar myToolbar;
    TextView fuel,name,make,type,year,power,option,price;
    Button but;
    ListView opt;
    Car car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        // find id's
        fuel = findViewById(R.id.carFuel);
        name = findViewById(R.id.carName);
        make = findViewById(R.id.carMake);
        type = findViewById(R.id.carType);
        year = findViewById(R.id.carYear);
        power = findViewById(R.id.carPower);
        price =  findViewById(R.id.price);
        but = findViewById(R.id.button2);
        Intent intent = getIntent();
        // get id from intent
        if(intent.hasExtra(Intent.EXTRA_TEXT)==true){
            String id = intent.getStringExtra(intent.EXTRA_TEXT);
            databaseHelper db = databaseHelper.getInstance(this);
            car = db.getCar(id);
            // set the text in the labels
            fuel.setText(car.fuel);
            name.setText(car.carName);
            make.setText(car.carMake);
            type.setText(car.carType);
            year.setText(car.year);
            power.setText(car.carPower);
            price.setText(car.price);
            opt = findViewById(R.id.options);
            option = findViewById(R.id.option);
            // create options list
            if(car.options.get(0) != null) {
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.option, R.id.option, car.options) {
                    //https://stackoverflow.com/questions/7389997/how-to-make-an-item-in-a-list-view-non-clickable-in-android/17906722
                    @Override
                    public boolean isEnabled(int position) {
                        return false;
                    }
                };
                opt.setAdapter(adapter);
            }
        }
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        but.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                databaseHelper db = databaseHelper.getInstance(carDetail.this);
                // if delete is pressed delete car and go back to mainActivity
                db.carDelete(car.id);
                Intent intent = new Intent(carDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
