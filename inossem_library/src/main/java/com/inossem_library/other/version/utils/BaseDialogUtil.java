package com.inossem_library.other.version.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.inossem_library.R;

public class BaseDialogUtil extends Dialog {
    private static Context mContext;
    private static Integer mLayoutResourceId;
    private static DialogListener mDialogListener;

    protected BaseDialogUtil(Context context) {
        this(context, R.style.BaseDialog);
    }

    protected BaseDialogUtil(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * @param context          上下文
     * @param layoutResourceId 布局文件
     * @param dialogListener   回调
     */
    public static BaseDialogUtil createDialog(Context context, Integer layoutResourceId, DialogListener dialogListener) {
        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mDialogListener = dialogListener;
        return new BaseDialogUtil(mContext);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(mLayoutResourceId, null);
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        relativeLayout.setBackgroundResource(R.drawable.bg_dialog);
        GradientDrawable myGrad = (GradientDrawable) relativeLayout.getBackground();
        Drawable drawable = view.getBackground();
        if (drawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            myGrad.setColor(colorDrawable.getColor());
        } else {
            myGrad.setColor(Color.WHITE);
        }
        int paddingInfo = 0;
        relativeLayout.setPadding(paddingInfo, paddingInfo, paddingInfo, paddingInfo);
        relativeLayout.addView(view);
        setContentView(relativeLayout);
        mDialogListener.initView(mContext, view, this);
    }

    public interface DialogListener {
        void initView(Context context, View view, Dialog dialog);
    }
}