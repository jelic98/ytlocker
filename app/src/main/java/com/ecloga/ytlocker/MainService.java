package com.ecloga.ytlocker;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.view.*;
import android.widget.ImageView;

public class MainService extends Service {

    private ImageView black, transparent;
    private WindowManager windowManager;
    private float lastX, lastY;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        black = new ImageView(this);
        black.setImageResource(R.drawable.black);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 0;

        windowManager.addView(black, params);

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

        windowManager.removeView(black);

        transparent = new ImageView(this);
        transparent.setImageResource(R.drawable.transparent);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 0;

        windowManager.addView(transparent, params);

        transparent.getLayoutParams().height = windowManager.getDefaultDisplay().getHeight();
        transparent.getLayoutParams().width = windowManager.getDefaultDisplay().getWidth();
    }
}
