package com.ecloga.ytlocker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PendingIntent pi = PendingIntent.getService(MainActivity.this,
                0, new Intent(MainActivity.this, MainService.class), 0);

        final Notification notification = new NotificationCompat.Builder(MainActivity.this)
                .setTicker("YTLocker")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("YTLocker")
                .setContentText("Click this to block touches")
                .setContentIntent(pi)
                .setOngoing(true)
                .build();

        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final Button btnToggle = (Button) findViewById(R.id.btnToggle);
        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnToggle.getText() == "Start") {
                    btnToggle.setText("Stop");
                    btnToggle.setBackgroundColor(Color.parseColor("#c0392b"));

                    notificationManager.notify(21098, notification);
                }else {
                    btnToggle.setText("Start");
                    btnToggle.setBackgroundColor(Color.parseColor("#27ae60"));

                    notificationManager.cancel(21098);
                }
            }
        });
    }
}
