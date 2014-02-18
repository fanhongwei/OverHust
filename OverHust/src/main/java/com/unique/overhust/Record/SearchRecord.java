package com.unique.overhust.Record;

import android.content.Context;

/**
 * Created by fhw on 2/18/14.
 */
public class SearchRecord {
    private DBManager dbManager;

    public SearchRecord(Context context) {
        dbManager = new DBManager(context);
    }

    public void add() {
        dbManager.add();
        dbManager.closeDB();
    }

    public void query() {
        dbManager.query();
        dbManager.closeDB();
    }

    public void delete() {
        dbManager.delete();
        dbManager.closeDB();
    }
}
