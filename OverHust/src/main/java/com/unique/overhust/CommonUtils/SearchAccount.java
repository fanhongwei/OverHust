package com.unique.overhust.CommonUtils;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by dsnc on 14-6-5.
 */
public class SearchAccount {
    private long id;
    private String name;
    private long longitude;
    private long latitude;
    private String category;
    private String picUrl;

    private static final HashMap<Long, SearchAccount> CACHE = new HashMap<Long, SearchAccount>();

    private static void addToCache(SearchAccount searchAccount) {
        CACHE.put(searchAccount.getId(), searchAccount);
    }

    private static SearchAccount getFromCache(long id) {
        return CACHE.get(id);
    }

    public static SearchAccount fromCursor(Context context, Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        SearchAccount mSearchAccount = getFromCache(id);
        if (mSearchAccount != null) {
            return mSearchAccount;
        } else {
            mSearchAccount = new SearchAccount();
            mSearchAccount.id = id;
            mSearchAccount.name = cursor.getString(cursor.getColumnIndex("name"));
            mSearchAccount.longitude = cursor.getLong(cursor.getColumnIndex("longitude"));
            mSearchAccount.latitude = cursor.getLong(cursor.getColumnIndex("latitude"));
            mSearchAccount.category = cursor.getString(cursor.getColumnIndex("category"));
            mSearchAccount.picUrl = cursor.getString(cursor.getColumnIndex("picUrl"));

            addToCache(mSearchAccount);

            return mSearchAccount;
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getLongitude() {
        return longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public String getCategory() {
        return category;
    }

    public String getPicUrl() {
        return picUrl;
    }
}
