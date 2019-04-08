package com.tugasmobile.searchmovieapplication.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.tugasmobile.searchmovieapplication.DetailMovieActivity;
import com.tugasmobile.searchmovieapplication.R;

import java.util.Calendar;
import java.util.List;

public class NotificationUpdateReminder extends BroadcastReceiver {

    private final static int ID_NOTIF = 100;
    private final static String CHANNEL_NOTIF = "101";
    private final static String DETAIL_FILM = "detail_film";
    private static int notifId = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        String judul = intent.getStringExtra("judul");
        int notifyid = intent.getIntExtra("notifyid", 0);
        long id = intent.getLongExtra("id", 0);
        String imageMovie = intent.getStringExtra("poster");
        String tanggalRilis = intent.getStringExtra("rilis");
        String deskripsi = intent.getStringExtra("deskripsi");
        MovieModel movieModel = new MovieModel(id, deskripsi, imageMovie, tanggalRilis, judul);
        String description = String.valueOf(R.string.hint);
        sendNotification(context, context.getString(R.string.app_name), description, notifyid, movieModel);
    }

    private void sendNotification(Context context, String title, String description, int id, MovieModel movieModel) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(DETAIL_FILM, movieModel);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(description)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_NOTIF, "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId(CHANNEL_NOTIF);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
        assert notificationManager != null;
        notificationManager.notify(id, builder.build());
    }

    public void setAlarm(Context context, List<MovieModel> modelFilmList) {
        int delay = 0;

        for (MovieModel modelFilm : modelFilmList) {
            cancelAlarm(context);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, NotificationUpdateReminder.class);
            intent.putExtra("judul", modelFilm.getJudul());
            intent.putExtra("id", modelFilm.getId());
            intent.putExtra("poster", modelFilm.getImageMovie());
            intent.putExtra("rilis", modelFilm.getTanggalRilis());
            intent.putExtra("deskripsi", modelFilm.getDeskripsi());
            intent.putExtra("notifyid", notifId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                assert alarmManager != null;
                alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + delay,
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                );
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                assert alarmManager != null;
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + delay, pendingIntent);
            }
            notifId += 1;
            delay += 3000;
        }
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.cancel(getPendingIntent(context));
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, NotificationUpdateReminder.class);
        return PendingIntent.getBroadcast(context, ID_NOTIF, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
