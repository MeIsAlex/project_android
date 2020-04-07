package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class databaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "carDatabase";
    private static final int DATABASE_VERSION = 6;

    // Table Names
    private static final String TABLE_CARS = "cars";
    private static final String TABLE_TYPES = "carTypes";
    private static final String TABLE_OPTIONS = "options";
    private static final String TABLE_OPTIONS_CAR = "optionsCar";
    private static final String TABLE_MAKE = "carMake";
    private static final String TABLE_FUEL = "carFuel";

    private static final String KEY_TYPE_ID = "id";
    private static final String KEY_TYPE_NAME = "name";

    private static final String KEY_FUEL_ID = "id";
    private static final String KEY_FUEL_NAME = "name";

    private static final String KEY_CAR_ID = "id";
    private static final String KEY_CAR_NAME = "name";
    private static final String KEY_CAR_POWER = "power";
    private static final String KEY_CAR_YEAR = "year";
    private static final String KEY_CAR_MAKE_FK = "makeId";
    private static final String KEY_CAR_TYPE_FK = "typeId";
    private static final String KEY_CAR_FUEL_FK = "fuelId";

    private static final String KEY_OPTIONS_ID = "id";
    private static final String KEY_OPTIONS_NAME = "name";

    private static final String KEY_MAKE_ID = "id";
    private static final String KEY_MAKE_NAME = "make";

    private static final String KEY_OPTION_CAR_CAR = "carId";
    private static final String KEY_OPTIONS_CAR_OPTIONS = "optionId";
    private static databaseHelper sInstance;
    private String[] options;
    public static synchronized databaseHelper getInstance(Context context) {
// https://guides.codepath.com/android/local-databases-with-sqliteopenhelper
        if (sInstance == null) {
            sInstance = new databaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public databaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        options = new String[]{
                "GPS",
                "ABS",
                "climate control",
                "parking sensor",
                "lane assist",
                "cruise control",
                "electric windows"

        };
    }
    public void  addCar(Car car){

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        int typeId= -1;
        int makeId= -1;
        int fuelId= -1;
        ArrayList<Integer> selOpt = new ArrayList<>(0);
        int index = 0;
        for (String opt :  car.options){
            if(Arrays.asList(options).contains(opt)){
                selOpt.add(index);
            }
            index++;
        }
        String GET_TYPE = "SELECT id FROM " + TABLE_TYPES + " WHERE " + KEY_TYPE_NAME + "= ?";
        Cursor cursor = db.rawQuery(GET_TYPE,new String[]{car.carType});
        if(cursor.moveToFirst()){
            typeId = cursor.getInt(cursor.getColumnIndex(KEY_TYPE_ID));
        }
        String GET_MAKE = "SELECT id FROM " + TABLE_MAKE + " WHERE " + KEY_MAKE_NAME + "= ?";
        cursor = db.rawQuery(GET_MAKE,new String[]{car.carMake});
        if(cursor.moveToFirst()){
            makeId = cursor.getInt(cursor.getColumnIndex(KEY_MAKE_ID));
        }
        if(makeId == -1){
            ContentValues val = new ContentValues();
            val.put(KEY_MAKE_NAME,car.carMake);
            makeId = (int)db.insertOrThrow(TABLE_MAKE,null,val);
        }
        String GET_FUEL = "SELECT id FROM " + TABLE_FUEL + " WHERE " + KEY_FUEL_NAME + "= ?";
        cursor = db.rawQuery(GET_FUEL,new String[]{car.fuel});
        if (cursor.moveToFirst()){
            fuelId = cursor.getInt(cursor.getColumnIndex(KEY_FUEL_ID));
        }
        ContentValues val = new ContentValues();
        val.put(KEY_CAR_NAME,car.carName);
        val.put(KEY_CAR_YEAR,car.year);
        val.put(KEY_CAR_POWER,car.carPower);
        val.put(KEY_CAR_FUEL_FK,fuelId);
        val.put(KEY_CAR_MAKE_FK,makeId);
        val.put(KEY_CAR_TYPE_FK,typeId);
        long carId =db.insert(TABLE_CARS,null,val);
        if(carId != -1){
            String INSERT_OPTIONS = "INSERT INTO " + TABLE_OPTIONS_CAR + "("+KEY_OPTIONS_CAR_OPTIONS+")" + " VALUES";
            index = 0;
            int temp = selOpt.get(0)+1;
            INSERT_OPTIONS += "("+temp+")";
            for (int opt : selOpt){
                if(index!=0) {
                    opt +=1;
                    INSERT_OPTIONS += ",(+opt+)";
                }
                index++;
            }
            db.execSQL(INSERT_OPTIONS);
        }
        String TESTQUERY = "SELECT * FROM " + TABLE_CARS;
        cursor = db.rawQuery(TESTQUERY,null);
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex(KEY_CAR_NAME));
        String t ="test";
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CAR = "CREATE TABLE " + TABLE_CARS +
                "(" +
                KEY_CAR_ID + " INTEGER PRIMARY KEY, " +
                KEY_CAR_NAME + " TEXT, " +
                KEY_CAR_POWER + " TEXT, " +
                KEY_CAR_YEAR + " TEXT, " +
                KEY_CAR_MAKE_FK + " INTEGER REFERENCES " + TABLE_MAKE + "," +
                KEY_CAR_TYPE_FK + " INTEGER REFERENCES " + TABLE_TYPES + "," +
                KEY_CAR_FUEL_FK + " INTEGER REFERENCES " + TABLE_FUEL +
                ")";
        String CREATE_FUEL = "CREATE TABLE " + TABLE_FUEL +
                "(" +
                KEY_FUEL_ID + " INTEGER PRIMARY KEY, " +
                KEY_FUEL_NAME + " TEXT" +
                ")";
        String CREATE_TYPES = "CREATE TABLE " + TABLE_TYPES +
                "(" +
                KEY_TYPE_ID + " INTEGER PRIMARY KEY, " +
                KEY_TYPE_NAME + " TEXT " +
                ")";
        String INSERT_TYPES = "INSERT INTO " + TABLE_TYPES + "("+KEY_TYPE_NAME+")" +
                " VALUES " +
                "('sedan')," +
                "('hatchback')," +
                "('minivan')," +
                "('SUV')," +
                "('crossover')," +
                "('coupé')," +
                "('convertible')";
    String INSERT_FUEL = "INSERT INTO " + TABLE_FUEL + "("+KEY_FUEL_NAME+")" +
            " VALUES " +
            "('petrol')," +
            "('diesel')," +
            "('hybrid'),"+
            "('electric')";
        String CREATE_OPTIONS = "CREATE TABLE " + TABLE_OPTIONS +
                "(" +
                KEY_OPTIONS_ID + " INTEGER PRIMARY KEY, " +
                KEY_OPTIONS_NAME + " TEXT " +
                ")";
        String CREATE_MAKE = "CREATE TABLE " + TABLE_MAKE +
                "(" +
                KEY_MAKE_ID + " INTEGER PRIMARY KEY, " +
                KEY_MAKE_NAME + " TEXT " +
                ")";
        String CREATE_OPTIONS_CAR = "CREATE TABLE " + TABLE_OPTIONS_CAR +
                "(" +
                KEY_OPTION_CAR_CAR + " INTEGER REFERENCES " + TABLE_CARS + "," +
                KEY_OPTIONS_CAR_OPTIONS + " INTEGER REFERENCES " + TABLE_OPTIONS_CAR +
                ")";
        String INSERT_OPTIONS = "INSERT INTO " + TABLE_OPTIONS + "("+KEY_OPTIONS_NAME+")" + " VALUES" + "('"+ options[0] +"')";
        for (String opt : options){
            if(opt == options[0]) continue;
            INSERT_OPTIONS += ",('"+opt+"')";
        }
        db.execSQL(CREATE_FUEL);
        db.execSQL(CREATE_MAKE);
        db.execSQL(CREATE_TYPES);
        db.execSQL(INSERT_TYPES);
        db.execSQL(CREATE_CAR);
        db.execSQL(CREATE_OPTIONS);
        db.execSQL(CREATE_OPTIONS_CAR);
        db.execSQL(INSERT_OPTIONS);
        db.execSQL(INSERT_FUEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPTIONS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPTIONS_CAR);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAKE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUEL);
            onCreate(db);
        }
    }
}
