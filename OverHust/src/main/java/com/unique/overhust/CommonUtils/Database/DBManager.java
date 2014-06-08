package com.unique.overhust.CommonUtils.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.unique.overhust.CommonUtils.Database.DBHelper;

/**
 * Created by fhw on 2/18/14.
 */
public class DBManager {
    private static DBHelper dbHelper;
    private static SQLiteDatabase db;

    public static Cursor mCursor;

    public DBManager(Context context) {

    }

    public static void open(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static void initDatabase(String name, long longitude, long latitude, String category, String picUrl) {
        try {
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("longitude", longitude);
            values.put("latitude", latitude);
            values.put("category", category);
            values.put("picUrl", picUrl);
            long rowid = db.insert("图片资源", null, values);
        } catch (Exception e) {
            Log.e("initdatabase", "" + e);
        }
    }

    public static void insert() {

    }

    public static Cursor getCursor() {
        mCursor = db.query("图片资源", null, null, null, null, null, null);
        return mCursor;
    }

    public static void delete() {

    }

    public static void close() {
        db.close();
    }
}
