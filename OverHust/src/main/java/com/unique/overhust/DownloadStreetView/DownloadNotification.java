package com.unique.overhust.DownloadStreetView;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.unique.overhust.CommonUtils.ShareContext;
import com.unique.overhust.R;

import java.math.BigDecimal;

/**
 * Created by fhw on 2/20/14.
 */
public class DownloadNotification {
    public static int DOWNLOAD_NOTIFICATION;

    private Notification notification;
    private NotificationManager notificationManager;

    private RemoteViews remoteViews;

    public DownloadNotification() {
        Log.e("notification", "ok");
    }

    public void initNotification() {
        try {
            notificationManager = (NotificationManager) ShareContext.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
            notification = new Notification();
            notification.tickerText = "OverHust";
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notification.when = System.currentTimeMillis();
            notification.icon = R.drawable.ic_launcher;

            remoteViews = new RemoteViews(ShareContext.getInstance().getPackageName(), R.layout.notification_download);
            notification.contentView = remoteViews;
        } catch (Exception e) {
            Log.e("notify", "" + e);
        }
    }

    public void showNotification() {
        initNotification();
        try {
            notificationManager.notify(DOWNLOAD_NOTIFICATION, notification);
        } catch (Exception e) {
            Log.e("notify", "" + e);
        }
    }

    public void removeNotification() {
        notificationManager.cancel(DOWNLOAD_NOTIFICATION);
    }

    public void updateProgressbar(int downloadSize, int fileSize, int count) {
            double k = (double) downloadSize / fileSize * 100;
            int i = (int) k;
            CharSequence downloadRate = String.valueOf(i) + "%";
            if (i >= 99) {
                removeNotification();
                DownloadCompleteNotification downloadCompleteNotification = new DownloadCompleteNotification();
            } else {
                remoteViews.setTextViewText(R.id.download_rate, downloadRate);
                remoteViews.setProgressBar(R.id.notification_progressbar, fileSize, downloadSize, false);
                //updateProgressRate(String.valueOf((int) ((downloadSize / fileSize) * 100)) + "%");
                notificationManager.notify(DOWNLOAD_NOTIFICATION, notification);
            }
    }
}
