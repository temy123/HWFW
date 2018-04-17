package com.naver.temy123.baseproject.base.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

/**
 * Created by temy1 on 2017-11-14.
 */

public class NotificationUtils {

    public static void notify(Context context, int id, int defaults, PendingIntent actionIntent) {
        Notification notification = null;
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        notification.defaults = defaults;
        notification.contentIntent = actionIntent;

        notification = builder.build();

        nm.notify(id, notification);
    }

}
