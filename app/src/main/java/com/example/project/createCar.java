package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class createCar extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Toolbar myToolbar;
    Spinner spinner;
    Button btn;
    TextView namel,powerl,yearl,makel,pricel;
    EditText power,name,year,make,price;
    RadioGroup group;
    LinearLayout checkboxes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_car);
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        checkboxes = findViewById(R.id.checkboxes);
        spinner = findViewById(R.id.spinner);
        // create dropdown
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,
                R.array.fueltype,
                R.layout.option
        );
        adapter.setDropDownViewResource(R.layout.option );
        // id's
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        namel = findViewById(R.id.nameLabel);
        makel = findViewById(R.id.makeLabel);
        powerl = findViewById(R.id.powerLabel);
        yearl = findViewById(R.id.yearLabel);
        btn = findViewById(R.id.button);
        price = findViewById(R.id.price);
        pricel = findViewById(R.id.pricel);
        group = findViewById(R.id.radioGroup);
        power = findViewById(R.id.power);
        name = findViewById(R.id.name);
        make = findViewById(R.id.make);
        year = findViewById(R.id.year);
        final ArrayList options = new ArrayList<String>();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if button is clicked get all values from form
                int selectId = group.getCheckedRadioButtonId();
                RadioButton radio = findViewById(selectId);
                Car newcar = new Car();
                newcar.carType = radio.getText().toString();
                newcar.fuel = spinner.getSelectedItem().toString();
                // get options
                for (int i = 0;i<checkboxes.getChildCount();i++){
                    CheckBox box = (CheckBox)checkboxes.getChildAt(i);
                    if (box.isChecked()){
                        options.add(box.getText().toString());
                    }
                }
                int status = 0;
                newcar.carName = name.getText().toString();
                newcar.carMake = make.getText().toString();
                newcar.carPower = power.getText().toString();
                newcar.price = price.getText().toString();
                newcar.year = year.getText().toString();
                newcar.options = options;
                // check if everything is filled in
                if(newcar.price.isEmpty()){
                    pricel.setTextColor(Color.parseColor("#FF0000"));
                    status = 1;
                }
                if(newcar.carName.isEmpty()){
                    namel.setTextColor(Color.parseColor("#FF0000"));
                    status = 1;
                }
                if(newcar.carMake.isEmpty()){
                    makel.setTextColor(Color.parseColor("#FF0000"));
                    status = 1;
                }
                if(newcar.carPower.isEmpty()){
                    powerl.setTextColor(Color.parseColor("#FF0000"));
                    status = 1;
                }
                if(newcar.year.isEmpty()){
                    yearl.setTextColor(Color.parseColor("#FF0000"));
                    status = 1;
                }
                if(status == 0){
                    // write a new car to the database
                    databaseHelper databasehelper = databaseHelper.getInstance(createCar.this);
                    databasehelper.addCar(newcar);
                    List<String> test = databasehelper.getCarNames();
                    // go back to main activity
                    Intent intent = new Intent(createCar.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    public void onRadioButtonClicked(View view) {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
