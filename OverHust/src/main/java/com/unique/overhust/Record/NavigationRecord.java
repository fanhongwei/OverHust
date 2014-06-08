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

    }

    public void query(String whichRecord) {

    }

    public void delete() {

    }
}
