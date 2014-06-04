package com.unique.overhust.Record;


import android.content.Context;

import com.unique.overhust.CommonUtils.Database.DBManager;

/**
 * Created by fhw on 2/18/14.
 */
public class NavigationRecord {
    private DBManager dbManager;

    public NavigationRecord(Context context) {
        dbManager = new DBManager(context);
    }

    public void add(String fromPlace,String toPlace,String date) {
        dbManager.add(fromPlace,toPlace,date);
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
