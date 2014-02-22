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
    private Thread downloadThread;

    @Override
    public void onCreate() {
        super.onCreate();

        downloadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                DownloadHustStreet downloadHustStreet = new DownloadHustStreet("http://overhuststreetview.qiniudn.com/street.zip", "street.zip");
                int result = downloadHustStreet.download();
                if (result == 0) {
                    try {
                        UnZip unZip = new UnZip(SDPATH + "street.zip", SDPATH);

                        //中止service
                        Intent intent = new Intent();
                        intent.setAction("com.unique.overhust.DownloadService");
                        stopService(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        downloadThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("service", "ok");

        Thread downThread = new Thread(new Runnable() {
            @Override
            public void run() {
                DownloadHustStreet downloadHustStreet = new DownloadHustStreet("http://overhuststreetview.qiniudn.com/street.zip", "street.zip");
                int result = downloadHustStreet.download();
                if (result == 0) {
                    try {
                        UnZip unZip = new UnZip(SDPATH + "street.zip", SDPATH);

                        //中止service
                        Intent intent = new Intent();
                        intent.setAction("com.unique.overhust.DownloadService");
                        stopService(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        downThread.start();

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
