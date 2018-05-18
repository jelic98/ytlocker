package org.ecloga.ytlocker;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.animation.*;
import android.widget.*;

public class MainService extends Service {

    private ImageView black;
    private TextView tvInfo;
    private WindowManager windowManager;
    private FrameLayout layout;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        layout = new FrameLayout(this);

        FrameLayout.LayoutParams flParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);

        black = new ImageView(this);
        black.setImageResource(R.drawable.black);

        DisplayMetrics metrics;
        metrics = getApplicationContext().getResources().getDisplayMetrics();

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/timeburnernormal.ttf");

        tvInfo = new TextView(this);
        tvInfo.setText("Hold to unlock");
        tvInfo.setTextColor(Color.parseColor("#ecf0f1"));
        tvInfo.setTypeface(font);

        float Textsize = tvInfo.getTextSize() / metrics.density;
        tvInfo.setTextSize(Textsize + 15);

        layout.addView(black);
        layout.addView(tvInfo, flParams);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 0;

        windowManager.addView(layout, params);

        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        black.startAnimation(fadeIn);

        black.getLayoutParams().height = windowManager.getDefaultDisplay().getHeight();
        black.getLayoutParams().width = windowManager.getDefaultDisplay().getWidth();

        black.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stopSelf();
                    }
                }, 500);

                return false;
            }
        });

        black.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.KEYCODE_VOLUME_DOWN) {
                    Toast.makeText(getApplicationContext(), "asd", Toast.LENGTH_LONG).show();
                }

                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        windowManager.removeView(layout);
    }
}
