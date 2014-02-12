package com.unique.overhust.DownloadStreetView;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by fhw on 2/11/14.
 */
public class DownloadService extends Service {
    private String SDPATH = Environment.getExternalStorageDirectory() + "/";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("service", "ok");

        Thread downThread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpDownloader httpDownloader = new HttpDownloader();
                int result = httpDownloader.download("http://overhuststreetview.qiniudn.com/street.zip", "", "street.zip");
                if (result == 0) {
                    try {
                        UnZip unZip = new UnZip(SDPATH + "street.zip", SDPATH);
                        Log.e("upzip", "ok");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("unzip",""+e);
                    }
                }
                Log.e("result", "" + result);
            }
        });
        downThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("service", "ok");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
