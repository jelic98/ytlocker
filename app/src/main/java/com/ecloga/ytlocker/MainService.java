package com.ecloga.ytlocker;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
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
        black.setImageResource(com.ecloga.ytlocker.R.drawable.black);


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
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
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
                stopSelf();
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
