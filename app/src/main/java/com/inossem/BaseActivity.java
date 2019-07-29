package com.inossem;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.inossem.util.Utils;

/**
 * 基础Activity类
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/7/23 14:59
 * @version Java-1.8
 * @since 1.0.0
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            super.onCreate(savedInstanceState);
            onCreateImpl(savedInstanceState);
        } catch (Throwable throwable) {
            Utils.e("", throwable);
        }
    }

    @Override
    protected void onStart() {
        try {
            super.onStart();
            onStartImpl();
        } catch (java.lang.Throwable Throwable) {
            Utils.e("", Throwable);
        }
    }

    @Override
    protected void onRestart() {
        try {
            super.onRestart();
            onRestartImpl();
        } catch (java.lang.Throwable Throwable) {
            Utils.e("", Throwable);
        }
    }

    @Override
    protected void onResume() {
        try {
            super.onResume();
            Utils.i("当前页面ClassName【" + this.getComponentName().getClassName() + "】");
            onResumeImpl();
        } catch (java.lang.Throwable Throwable) {
            Utils.e("", Throwable);
        }
    }

    @Override
    protected void onPause() {
        try {
            super.onPause();
            onPauseImpl();
        } catch (java.lang.Throwable Throwable) {
            Utils.e("", Throwable);
        }
    }

    @Override
    protected void onStop() {
        try {
            super.onStop();
            onStopImpl();
        } catch (java.lang.Throwable Throwable) {
            Utils.e("", Throwable);
        }

    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
            onDestroyImpl();
        } catch (java.lang.Throwable Throwable) {
            Utils.e("", Throwable);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            onActivityResultImpl(requestCode, resultCode, data);
        } catch (java.lang.Throwable Throwable) {
            Utils.e("", Throwable);
        }
    }

    @Override
    protected void onUserLeaveHint() {
        try {
            super.onUserLeaveHint();
            onUserLeaveHintImpl();
        } catch (java.lang.Throwable Throwable) {
            Utils.e("", Throwable);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        try {
            super.onWindowFocusChanged(hasFocus);
            onWindowFocusChangedImpl(hasFocus);
        } catch (java.lang.Throwable Throwable) {
            Utils.e("", Throwable);
        }
    }

    protected void onCreateImpl(Bundle savedInstanceState) {
    }

    protected void onStartImpl() {
    }

    protected void onRestartImpl() {
    }

    protected void onResumeImpl() {
    }

    protected void onPauseImpl() {
    }

    protected void onStopImpl() {
    }

    protected void onDestroyImpl() {
    }

    protected void onActivityResultImpl(int requestCode, int resultCode, Intent data) {

    }

    protected void onUserLeaveHintImpl() {
    }

    public void onWindowFocusChangedImpl(boolean hasFocus) {
    }

}
