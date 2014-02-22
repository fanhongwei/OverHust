package com.unique.overhust.Record;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
        try {
            db.execSQL("insert into NavigationRecord values" + "(?,'" + fromPlace + "','" + toPlace + "'," + date + ")");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("addnav",""+e);
        }
        Log.e("addnav","ok");
    }

    public void add(String whichPlace, String date) {
        try {
            db.execSQL("insert into SearchRecord values" + "(?,'" + whichPlace + "'," + date + ")");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("addsear",""+e);
        }
        Log.e("addsear", "ok");
    }

    public Cursor query(String whichRecord) {

        if (whichRecord.equals("navigation")) {
            queryInfo = db.rawQuery("select * from NavigationRecord", null);

        } else {
            queryInfo = db.rawQuery("select * from SearchRecord", null);
        }

        Log.e("database", "" + queryInfo);

        return queryInfo;
    }

    public void delete() {

    }

    public void closeDB() {
        db.close();
    }
}
