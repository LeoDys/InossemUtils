package com.inossem_library.other.version.utils;

import android.view.View;

import java.util.Calendar;

public abstract class NoDoubleClickListener implements View.OnClickListener {

    private static final int DEFAULT_MIN_CLICK_DELAY_TIME = 500;
    private long lastClickTime = 0;
    private long mDelayTime;

    public NoDoubleClickListener(long delayTime) {
        if (delayTime < -1) {
            throw new RuntimeException("parameter must not be less than -1!");
        }
        mDelayTime = delayTime;
    }

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (mDelayTime == -1) {
            onNoDoubleClick(v);
        } else if (mDelayTime == 0) {
            if (currentTime - lastClickTime > DEFAULT_MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                onNoDoubleClick(v);
            }
        } else if (mDelayTime > 0) {
            if (currentTime - lastClickTime > mDelayTime) {
                lastClickTime = currentTime;
                onNoDoubleClick(v);
            }
        }
    }

    public NoDoubleClickListener() {
    }

    public abstract void onNoDoubleClick(View v);
}