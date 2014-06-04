package com.unique.overhust.Record;

import android.content.Context;

import com.unique.overhust.CommonUtils.Database.DBManager;

/**
 * Created by fhw on 2/18/14.
 */
public class SearchRecord {
    private DBManager dbManager;

    public SearchRecord(Context context) {
        dbManager = new DBManager(context);
    }

    public void add(String whichPlace,String date) {
        dbManager.add(whichPlace,date);
        dbManager.closeDB();
    }

    public void query(String whichRecord) {
        dbManager.query(whichRecord);
        dbManager.closeDB();
    }

    public void delete() {
        dbManager.delete();
        dbManager.closeDB();
    }
}
