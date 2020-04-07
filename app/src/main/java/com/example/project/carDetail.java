package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class carDetail extends AppCompatActivity {
    Toolbar myToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        TextView text = findViewById(R.id.testtext);
        Intent intent = getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT)==true){
            String t = intent.getStringExtra(intent.EXTRA_TEXT);
            text.setText(t);
        }
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }
}
