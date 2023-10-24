package com.example.actividad7;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.PendingIntent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    NotificationManager notificationManager;
    static final String CANAL_ID = "SecondChannel";
    static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        createNotificationChannel();

        button.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://darling-in-the-franxx.fandom.com/es/wiki/Zero_Two"));
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0,intent, PendingIntent.FLAG_IMMUTABLE);
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });
    }

    private void createNotificationChannel() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CANAL_ID, "Mis Notificaciones", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Este canal es exclusivo del Negro");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 100, 300, 100});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void sendNotification() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CANAL_ID)
                .setContentTitle("Notificación mamalona")
                .setContentText("QUIERO DORMIRRRRR")
                .setSmallIcon(R.mipmap.ic_launcher);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}