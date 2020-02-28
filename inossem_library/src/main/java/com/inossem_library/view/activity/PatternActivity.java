package com.inossem_library.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.inossem_library.R;
import com.inossem_library.other.pattern.constant.PatternConstant;
import com.inossem_library.tips.toast.util.ToastUtils;

import java.util.List;

/**
 * 手势密码活动类
 *
 * @author LeoDys E-mail:changwen.sun@inossem.com 2020/2/27 14:16
 * @version 1.0.8
 * @since 1.0.8
 */

public class PatternActivity extends Activity implements View.OnClickListener {

    private Activity mActivity;
    // 手势控件
    private PatternLockView mPatternLockView;
    // 页面上方的logo 一般为项目的logo
    private ImageView img_logo;
    // 提示消息 重置按钮 确认按钮 忘记密码
    private TextView tv_message, tv_reset, tv_confirm, tv_forget;
    // 重置  取消按钮布局  addView
    private RelativeLayout bottomLayout;
    /**
     * correctPassword  正确密码
     * firstPassword    第一次输入的密码
     * confirmPassword  第二次输入的密码
     * type             操作类型
     */
    private String correctPassword, firstPassword, confirmPassword, type;
    // 图片资源
    private int logoResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = PatternActivity.this;
        //去掉标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pattern);
        initView();
        getExtra();
        checkType();
        initLogo();
        mPatternLockView.addPatternLockListener(patternLockViewListener);
        initType();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mPatternLockView = this.findViewById(R.id.patternLockView);
        mPatternLockView.setInStealthMode(true);
        mPatternLockView.setTactileFeedbackEnabled(true);
        img_logo = this.findViewById(R.id.logo);
        tv_message = this.findViewById(R.id.message);
        bottomLayout = this.findViewById(R.id.bottomLayout);
    }

    /**
     * 获取上个页面传过来的值
     */
    private void getExtra() {
        type = getIntent().getStringExtra(PatternConstant.KEY_TYPE);
        correctPassword = getIntent().getStringExtra(PatternConstant.KEY_CORRECT_PASSWORD);
        logoResource = getIntent().getIntExtra(PatternConstant.KEY_LOGO, 0);
    }

    /**
     * 检查必须参数是否齐全
     */
    private void checkType() {
        if (TextUtils.isEmpty(type)) {
            ToastUtils.showError(PatternActivity.this, "类型参数必须传递", null);
            finish();
        }
        if (!type.equals(PatternConstant.TYPE_SET_PASSWORD) && TextUtils.isEmpty(correctPassword)) {
            ToastUtils.showError(PatternActivity.this, "未设置手势密码", null);
            finish();
        }
    }

    /**
     * 设置页面顶部logo
     */
    private void initLogo() {
        if (logoResource == 0) {
            return;
        }
        img_logo.setImageResource(logoResource);
    }

    /**
     * 设置操作类型
     */
    private void initType() {
        switch (type) {
            case PatternConstant.TYPE_SET_PASSWORD:
                // 设置密码
                initSetPassword();
                break;
            case PatternConstant.TYPE_CHANGE_PASSWORD:
                // 修改密码
                initChangePassword();
                break;
            case PatternConstant.TYPE_CHECK_PASSWORD:
                // 校验密码
                initCheckPassword();
                break;
        }
    }

    /**
     * 设置密码
     */
    private void initSetPassword() {
        mPatternLockView.clearPattern();
        tv_message.setText("请绘制手势密码");
        View setPasswordView = LayoutInflater.from(this).inflate(R.layout.layout_set_password, null);
        tv_reset = setPasswordView.findViewById(R.id.reset);
        tv_confirm = setPasswordView.findViewById(R.id.confirm);
        tv_reset.setVisibility(View.INVISIBLE);
        tv_confirm.setVisibility(View.INVISIBLE);
        bottomLayout.removeAllViews();
        bottomLayout.addView(setPasswordView);
    }

    /**
     * 修改密码
     */
    private void initChangePassword() {
        mPatternLockView.clearPattern();
        tv_message.setText("请绘制原始手势密码");
        View changePasswordView = LayoutInflater.from(this).inflate(R.layout.layout_forget_password, null);
        tv_forget = changePasswordView.findViewById(R.id.forget);
        tv_forget.setOnClickListener(PatternActivity.this);
        bottomLayout.removeAllViews();
        // 隐藏忘记密码
        tv_forget.setVisibility(View.GONE);
//        bottomLayout.addView(changePasswordView);
    }

    /**
     * 校验密码
     */
    private void initCheckPassword() {
        mPatternLockView.clearPattern();
        tv_message.setText("请输入手势密码");
        View checkPasswordView = LayoutInflater.from(this).inflate(R.layout.layout_forget_password, null);
        tv_forget = checkPasswordView.findViewById(R.id.forget);
        tv_forget.setOnClickListener(PatternActivity.this);
        bottomLayout.removeAllViews();
//        bottomLayout.addView(checkPasswordView);
    }

    /**
     * 设置密码
     *
     * @param result
     */
    private void doSetPassword(String result) {
        if (TextUtils.isEmpty(firstPassword)) {
            // 第一次输入密码
            firstPassword = result;
            tv_reset.setVisibility(View.VISIBLE);
            tv_confirm.setVisibility(View.VISIBLE);
            tv_reset.setOnClickListener(PatternActivity.this);
            tv_reset.setTextColor(PatternActivity.this.getResources().getColor(android.R.color.holo_blue_dark));
            tv_message.setText("请再次绘制手势密码");
            mPatternLockView.clearPattern();
        } else {
            // 第二次输入密码
            if (firstPassword.equals(result)) {
                // 两次密码一致
                confirmPassword = result;
                tv_confirm.setOnClickListener(PatternActivity.this);
                tv_confirm.setTextColor(PatternActivity.this.getResources().getColor(android.R.color.holo_blue_dark));
                mPatternLockView.setPattern(PatternLockView.PatternViewMode.CORRECT, PatternLockUtils.stringToPattern(mPatternLockView, confirmPassword));
                tv_message.setText("手势密码绘制成功");
                mPatternLockView.removePatternLockListener(patternLockViewListener);
                tv_reset.setOnClickListener(null);
                tv_reset.setTextColor(PatternActivity.this.getResources().getColor(android.R.color.white));
                mPatternLockView.setInputEnabled(false);
            } else {
                // 两次密码不一致
                mPatternLockView.setPattern(PatternLockView.PatternViewMode.WRONG, PatternLockUtils.stringToPattern(mPatternLockView, result));
                tv_message.setText("两次手势密码不一致,请重试");
            }
        }
    }

    /**
     * 修改手势密码
     *
     * @param result 新待验证的手势密码
     */
    private void doChangePassword(String result) {
        if (correctPassword.equals(result)) {
            // 验证通过  设置为设置密码状态
            type = PatternConstant.TYPE_SET_PASSWORD;
            initSetPassword();
        } else {
            mPatternLockView.setPattern(PatternLockView.PatternViewMode.WRONG, PatternLockUtils.stringToPattern(mPatternLockView, result));
            tv_message.setText("原始手势密码绘制错误,请重试");
        }
    }

    /**
     * 检查密码是否正确
     *
     * @param result 新待验证的手势密码
     */
    private void doCheckPassword(String result) {
        if (correctPassword.equals(result)) {
            // 验证成功直接返回
            mPatternLockView.setPattern(PatternLockView.PatternViewMode.CORRECT, PatternLockUtils.stringToPattern(mPatternLockView, result));
            forResult(PatternConstant.RESULT_TYPE_CHECK_SUCCESS);
        } else {
            mPatternLockView.setPattern(PatternLockView.PatternViewMode.WRONG, PatternLockUtils.stringToPattern(mPatternLockView, result));
            tv_message.setText("手势密码绘制错误,请重试");
        }
    }

    /**
     * 手势密码绘制完成的监听
     */
    private PatternLockViewListener patternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
        }

        @Override
        public void onComplete(final List<PatternLockView.Dot> pattern) {
            String result = PatternLockUtils.patternToString(mPatternLockView, pattern);
            if (checkPasswordLength(result)) {
                switch (type) {
                    case PatternConstant.TYPE_SET_PASSWORD:
                        doSetPassword(result);
                        break;
                    case PatternConstant.TYPE_CHANGE_PASSWORD:
                        doChangePassword(result);
                        break;
                    case PatternConstant.TYPE_CHECK_PASSWORD:
                        doCheckPassword(result);
                        break;
                }
            }
        }

        @Override
        public void onCleared() {
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.reset) {
            tv_reset.setVisibility(View.INVISIBLE);
            tv_confirm.setVisibility(View.INVISIBLE);
            tv_reset.setOnClickListener(null);
            tv_confirm.setOnClickListener(null);
            tv_reset.setTextColor(PatternActivity.this.getResources().getColor(android.R.color.white));
            tv_confirm.setTextColor(PatternActivity.this.getResources().getColor(android.R.color.white));
            firstPassword = null;
            confirmPassword = null;
            mPatternLockView.clearPattern();
            tv_message.setText("请绘制手势密码");
        } else if (id == R.id.confirm) {
            forResult(PatternConstant.RESULT_TYPE_CONFIRM);
        } else if (id == R.id.forget) {
            forResult(PatternConstant.RESULT_TYPE_FORGET);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    /**
     * 返回上一页面
     *
     * @param type 操作类型   区分setResult携带值类型
     */
    private void forResult(String type) {
        switch (type) {
            case PatternConstant.RESULT_TYPE_CONFIRM:
                Intent confirmIntent = new Intent();
                confirmIntent.putExtra(PatternConstant.KEY_NEW_PASSWORD, confirmPassword);
                confirmIntent.putExtra(PatternConstant.KEY_RESULT_TYPE, PatternConstant.RESULT_TYPE_CONFIRM);
                setResult(Activity.RESULT_OK, confirmIntent);
                finish();
                break;
            case PatternConstant.RESULT_TYPE_FORGET:
                Intent forgetIntent = new Intent();
                forgetIntent.putExtra(PatternConstant.KEY_RESULT_TYPE, PatternConstant.RESULT_TYPE_FORGET);
                setResult(Activity.RESULT_OK, forgetIntent);
                finish();
                break;
            case PatternConstant.RESULT_TYPE_CHECK_SUCCESS:
                Intent checkSuccessIntent = new Intent();
                checkSuccessIntent.putExtra(PatternConstant.KEY_RESULT_TYPE, PatternConstant.RESULT_TYPE_CHECK_SUCCESS);
                setResult(Activity.RESULT_OK, checkSuccessIntent);
                finish();
                break;
        }
    }

    /**
     * 校验手势密码是否合法
     *
     * @param result 待验证的手势密码
     * @return 是否合法
     */
    private boolean checkPasswordLength(String result) {
        if (result.length() < PatternConstant.PASSWORD_LENGTH) {
            tv_message.setText("手势密码最短4位,请重新绘制");
            mPatternLockView.setPattern(PatternLockView.PatternViewMode.WRONG,
                    PatternLockUtils.stringToPattern(mPatternLockView, result));
            return false;
        }
        return true;
    }
}
