package com.unique.overhust.CommonUtils.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.unique.overhust.CommonUtils.Database.DBHelper;

/**
 * Created by fhw on 2/18/14.
 */
public class DBManager {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public Cursor queryInfo;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void add(String fromPlace, String toPlace, String date) {

    }

    public void add(String whichPlace, String date) {

    }

    public Cursor query(String whichRecord) {

        return queryInfo;
    }

    public void delete() {

    }

    public void closeDB() {
        db.close();
    }
}
