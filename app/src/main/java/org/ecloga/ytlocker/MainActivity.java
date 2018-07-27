package org.ecloga.ytlocker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

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
        btnToggle.setTextSize(Textsize + 8);

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvAuthor = (TextView) findViewById(R.id.tvAuthor);

        tvTitle.setTypeface(font);
        tvAuthor.setTypeface(font);

        tvTitle.setTextSize(Textsize + 20);
        tvAuthor.setTextSize(Textsize + 2);

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

        final Notification notification = new NotificationCompat.Builder(MainActivity.this, "55555")
                .setTicker("YTLocker")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("YTLocker")
                .setContentText("Click this to block touches")
                .setContentIntent(PendingIntent.getService(this,
                        0, new Intent(this, MainService.class), 0))
                .setOngoing(true)
                .build();

        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                Animation zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                if(Build.VERSION.SDK_INT >= 26) {
                    NotificationChannel channel =
                            new NotificationChannel("55555", "YTLocker", NotificationManager.IMPORTANCE_LOW);
                    channel.enableLights(true);
                    channel.setLightColor(Color.BLUE);
                    channel.setShowBadge(false);
                    manager.createNotificationChannel(channel);
                }

                if(btnToggle.getText() == "Start") {
                    btnToggle.setText("Stop");
                    btnToggle.setBackground(getResources().getDrawable(R.drawable.stop));

                    manager.notify(55555, notification);

                    editor.putString("started", "true");
                }else {
                    btnToggle.setText("Start");
                    btnToggle.setBackground(getResources().getDrawable(R.drawable.start));

                    manager.cancel(55555);

                    editor.putString("started", "false");
                }

                btnToggle.startAnimation(zoom);
                editor.apply();
            }
        });
    }
}
