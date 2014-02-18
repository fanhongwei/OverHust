package com.unique.overhust.Record;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fhw on 2/18/14.
 */
public class DBManager {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void add(){

    }

    public void query(){

    }

    public void delete(){

    }

    public void closeDB(){
        db.close();
    }
}
