package com.ecloga.ytlocker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/timeburnernormal.ttf");
        final Button btnToggle = (Button) findViewById(R.id.btnToggle);
        btnToggle.setTypeface(font);
        btnToggle.setTextColor(Color.parseColor("#ecf0f1"));

        DisplayMetrics metrics;
        metrics = getApplicationContext().getResources().getDisplayMetrics();
        float Textsize = btnToggle.getTextSize() / metrics.density;
        btnToggle.setTextSize(Textsize + 7);


        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        btnToggle.setHeight(Math.min(height, width) / 3);
        btnToggle.setWidth(Math.min(height, width) / 3);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String started = prefs.getString("started", "");

        if(started.equals("true")) {
            btnToggle.setText("Stop");
            btnToggle.setBackground(getResources().getDrawable(R.drawable.stop));
        }else {
            btnToggle.setText("Start");
            btnToggle.setBackground(getResources().getDrawable(R.drawable.start));
        }

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

        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                Animation zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);

                if(btnToggle.getText() == "Start") {
                    btnToggle.setText("Stop");
                    btnToggle.setBackground(getResources().getDrawable(R.drawable.stop));

                    notificationManager.notify(21098, notification);

                    editor.putString("started", "true");
                }else {
                    btnToggle.setText("Start");
                    btnToggle.setBackground(getResources().getDrawable(R.drawable.start));

                    notificationManager.cancel(21098);

                    editor.putString("started", "false");
                }

                btnToggle.startAnimation(zoom);
                editor.apply();
            }
        });
    }
}
