package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.UiModeManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity implements carsAdapter.OnCarListener,SharedPreferences.OnSharedPreferenceChangeListener {
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
        //get information cars from database
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
        //create adapter for carlist
        carsAdapter adapter = new carsAdapter(carList,makeList,this);
        cars.setAdapter(adapter);
        //set layout
        cars.setLayoutManager(new LinearLayoutManager(this));
        setupSharedPreferences();
        //get shared preferences
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        //get day/nightmode pref
        String ret = settings.getString("night", "false");
        //set night if true
        if(Boolean.parseBoolean(ret)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
    private void setupSharedPreferences() {
        //setup preferences
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }
    private void openadd() {
        //start createcar Activity when actionButton is pressed
        Intent intent = new Intent(this,createCar.class);
        startActivity(intent);
    }

    @Override
    public void onCarClick(int pos) {
        //open cardetail when car is clicked
        Intent intent = new Intent(this, carDetail.class);
        intent.putExtra(Intent.EXTRA_TEXT, String.valueOf(idList.get(pos)));

        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //create option menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // open settings
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //if setting changes check if dark/lightmode changes
        if(key.equals("darkmode")){
            boolean res = sharedPreferences.getBoolean(key,false);
            if(res == false){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = settings.edit();
            // write the value away so it's saved when app gets restarted
            editor.putString("night", String.valueOf(res));
            editor.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

}