package com.unique.overhust.DownloadStreetView;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.unique.overhust.CommonUtils.ShareContext;
import com.unique.overhust.MainActivity.MainActivity;
import com.unique.overhust.MainActivity.StartActivity;
import com.unique.overhust.R;

/**
 * Created by fhw on 2/22/14.
 */
public class DownloadCompleteNotification {
    public static int DOWNLOAD_NOTIFICATION_COMPLETE;

    private Notification notification;
    private NotificationManager notificationManager;

    private RemoteViews remoteViews;

    public DownloadCompleteNotification() {
        notificationManager = (NotificationManager) ShareContext.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.tickerText = "OverHust离线包下载完毕";
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.when = System.currentTimeMillis();
        notification.icon = R.drawable.ic_launcher;
        long[] patter = {50, 150};
        notification.vibrate = patter;
        remoteViews = new RemoteViews(ShareContext.getInstance().getPackageName(), R.layout.notification_download_complete);

        notification.contentView = remoteViews;

        notificationManager.notify(DOWNLOAD_NOTIFICATION_COMPLETE, notification);
    }
}
