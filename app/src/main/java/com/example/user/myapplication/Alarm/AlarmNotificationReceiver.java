package com.example.user.myapplication.Alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;

import com.example.user.myapplication.Fragment.DirectionFragment;
import com.example.user.myapplication.ListViewSecondActivity;
import com.example.user.myapplication.R;

public class AlarmNotificationReceiver  extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock w1 = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "Turn On Screen");
        w1.acquire();
        //Uri ringtoneLucky = RingtoneManager.getDefaultUri(R.raw.lucky);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setContentInfo("Alarm");

        if (ListViewSecondActivity.alarm_mode==0 || ListViewSecondActivity.alarm_mode==2){
            builder.setVibrate(new long[]{1000, 1000});
        }
        if (ListViewSecondActivity.alarm_mode==1 || ListViewSecondActivity.alarm_mode==2){
            builder.setDefaults(Notification.DEFAULT_LIGHTS);
            if (ListViewSecondActivity.ringtone_mode==0) {
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            }
            else if (ListViewSecondActivity.ringtone_mode==1){
                builder.setSound(Uri.parse("android.resource://com.example.user.myapplication/" + R.raw.lucky3));
            }
            else if (ListViewSecondActivity.ringtone_mode==2){
                builder.setSound(Uri.parse("android.resource://com.example.user.myapplication/" + R.raw.lucky2));
            }
        }
        if(DirectionFragment.jenisAlarm==1){
            builder.setContentTitle("Siap - siap!")
                    .setContentText("Sebentar lagi Anda akan sampai ke tujuan");
        }
        else if(DirectionFragment.jenisAlarm==0){
            builder.setContentTitle("Anda sudah sampai!")
                    .setContentText("Saatnya Anda turun");
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

        try {
            Thread.sleep(0);
            if (w1.isHeld()){
                w1.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}