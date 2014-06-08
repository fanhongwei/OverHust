package com.unique.overhust.CommonUtils;

import android.database.Cursor;

import com.unique.overhust.CommonUtils.Database.DBManager;

import java.util.ArrayList;

/**
 * Created by fhw on 12/5/13.
 */
public class SearchCheeses {
    public static ArrayList<String> mArrayList = new ArrayList<String>();

    public static ArrayList<String> getCheeses() {
        getData();
        return mArrayList;
    }

    public static void getData() {
        DBManager.open(ShareContext.getInstance());
        Cursor mCursor = DBManager.getCursor();
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mArrayList.add(mCursor.getString(mCursor.getColumnIndex("name")));
            mCursor.moveToNext();
        }

        mArrayList.add("学术交流中心");
        mArrayList.add("住宿区");
        mArrayList.add("教学楼");
        mArrayList.add("餐厅");
        mArrayList.add("学院楼");
        mArrayList.add("超市");
        mArrayList.add("澡堂");
        mArrayList.add("服务设施");
        mArrayList.add("体育馆");
        mArrayList.add("图书馆");
        mArrayList.add("行政区");
        mArrayList.add("华科美景");

        DBManager.close();
    }
}
