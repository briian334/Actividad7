package com.example.actividad7;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://darling-in-the-franxx.fandom.com/es/wiki/Zero_Two"));
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
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

        // Intent para abrir el enlace web
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://darling-in-the-franxx.fandom.com/es/wiki/Zero_Two"));
        PendingIntent webPendingIntent = PendingIntent.getActivity(this, 0, webIntent, PendingIntent.FLAG_IMMUTABLE);

        // Intent para realizar una llamada
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:6145112734")); // Reemplaza "123456789" con el número de teléfono que deseas
        PendingIntent callPendingIntent = PendingIntent.getActivity(this, 1, callIntent, PendingIntent.FLAG_IMMUTABLE);

        // Intent para abrir Google Maps
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=lat,lng");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        PendingIntent mapPendingIntent = PendingIntent.getActivity(this, 2, mapIntent, PendingIntent.FLAG_IMMUTABLE);

        // Agregar las acciones a la notificación
        notificationBuilder.setContentIntent(webPendingIntent); // Acción principal al tocar la notificación
        notificationBuilder.addAction(0, "Página Web", webPendingIntent); // Abrir página web
        notificationBuilder.addAction(0, "Llamar", callPendingIntent); // Realizar llamada
        notificationBuilder.addAction(0, "Maps", mapPendingIntent); // Abrir Maps

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}
