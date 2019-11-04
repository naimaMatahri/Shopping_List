package com.example.list;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, "ShoppingCart.db", null, 1);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {

        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    //create the tables in the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS unbought_items" +
                "(item_name TEXT ,item_quantity TEXT ,item_price TEXT ,item_cost TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS bought_items" +
                "(item_name TEXT ,item_quantity TEXT ,item_price TEXT ,item_cost TEXT );");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS unbought_items");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS bought_items");

        onCreate(sqLiteDatabase);
    }


}

