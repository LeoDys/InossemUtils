package com.inossem_library.tips.dialog.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.inossem_library.R;
import com.inossem_library.exception.ExceptionEnum;
import com.inossem_library.exception.InossemException;
import com.inossem_library.tips.dialog.constant.DialogConstants;
import com.inossem_library.tips.dialog.constant.DialogTypeEnum;

/**
 * @author 王斯宇
 * @time on 2019/7/25 14:47
 * @email siyu.wang@inossem.com
 */
public class DialogUtils {

    private NiftyDialogBuilder dialogBuilder;

    /**
     * 构造方法
     */
    private DialogUtils() {

    }
    /**
     * 获取NiftyDialogUtils实例
     * @return
     */
    private static class NiftyDialogEffectsUtilsHolder {
        private final static DialogUtils instance = new DialogUtils();
    }

    /**
     * 获取NiftyDialogUtils实例
     * @return
     */
    public static DialogUtils getInstance() {
        return NiftyDialogEffectsUtilsHolder.instance;
    }

    /**
     * 显示提示信息
     * @param context  上下文
     * @param mInfo  提示信息
     * @return
     */
    public Dialog showInfoDialog(Context context, String mInfo) {
        return showInfoDialog(context, null, mInfo, true, true ,null);
    }

    /**
     * 显示提示信息
     * @param context  上下文
     * @param mTitle  标题
     * @param mInfo  提示信息
     * @param haveButton  是否有按钮
     * @param confirmListener  按钮点击事件
     * @param isCancelableOnTouchOutside 点击dialog外部是否消失
     * @return
     */
    public Dialog showInfoDialog(Context context, String mTitle, String mInfo, boolean haveButton, Boolean isCancelableOnTouchOutside ,View.OnClickListener confirmListener) {
        return showDialog(context, null, mTitle, mInfo, DialogTypeEnum.INFO, haveButton, null ,isCancelableOnTouchOutside , confirmListener, null);
    }

    /**
     * 显示警告信息
     * @param context  上下文
     * @param mInfo  提示信息
     * @return
     */
    public Dialog showWarmingDialog(Context context, String mInfo) {
        return showWarmingDialog(context, null, mInfo, true, true ,null);
    }
    /**
     * 显示警告信息
     * @param context  上下文
     * @param mInfo  提示信息
     *  @param effectstype  动画效果  Effectstype.Fadein(默认效果)  Effectstype.Slideleft(左侧划入)  Effectstype.Slidetop(上部划入)   Effectstype.SlideBottom(底部划入)   Effectstype.Slideright(右侧划入)   Effectstype.Fall(缩放)
     *            Effectstype.Newspager(左右旋转)   Effectstype.Fliph(上下翻转)  Effectstype.RotateBottom(从下部翻转划入)  Effectstype.RotateLeft(从左侧翻转划入)   Effectstype.Slit(翻转)  Effectstype.Shake(抖动)  Effectstype.Shake(Sidefill)
     * @return
     */
    public Dialog showWarmingDialog(Context context, String mInfo , Effectstype effectstype) {
        return showWarmingDialog(context, null, mInfo, effectstype ,true, true ,null);
    }
    /**
     * 显示警告信息
     * @param context  上下文
     * @param mTitle  标题
     * @param mInfo  提示信息
     * @param haveButton  是否有按钮
     * @param confirmListener  按钮点击事件
     * @param isCancelableOnTouchOutside 点击dialog外部是否消失
     * @return
     */
    public Dialog showWarmingDialog(Context context, String mTitle, String mInfo, boolean haveButton,Boolean isCancelableOnTouchOutside , View.OnClickListener confirmListener) {
        return showDialog(context, null, mTitle, mInfo, DialogTypeEnum.WARMING, haveButton, null ,isCancelableOnTouchOutside , confirmListener, null);
    }
    /**
     * 显示警告信息
     * @param context  上下文
     * @param mTitle  标题
     * @param mInfo  提示信息
     * @param haveButton  是否有按钮
     * @param confirmListener  按钮点击事件
     * @param isCancelableOnTouchOutside 点击dialog外部是否消失
     * @return
     */
    public Dialog showWarmingDialog(Context context, String mTitle, String mInfo  , Effectstype effectstype, boolean haveButton,Boolean isCancelableOnTouchOutside , View.OnClickListener confirmListener) {
        return showDialog(context, null, mTitle, mInfo, DialogTypeEnum.WARMING, haveButton, effectstype ,isCancelableOnTouchOutside , confirmListener, null);
    }

    /**
     * 显示错误信息
     * @param context  上下文
     * @param mInfo  提示信息
     * @param effectstype  动画效果  Effectstype.Fadein(默认效果)  Effectstype.Slideleft(左侧划入)  Effectstype.Slidetop(上部划入)   Effectstype.SlideBottom(底部划入)   Effectstype.Slideright(右侧划入)   Effectstype.Fall(缩放)
     *            Effectstype.Newspager(左右旋转)   Effectstype.Fliph(上下翻转)  Effectstype.RotateBottom(从下部翻转划入)  Effectstype.RotateLeft(从左侧翻转划入)   Effectstype.Slit(翻转)  Effectstype.Shake(抖动)  Effectstype.Shake(Sidefill)
     * @return
     */
    public Dialog showErrorDialog(Context context, String mInfo , Effectstype effectstype) {
        return showErrorDialog(context, null, mInfo, true,effectstype ,true , null);
    }
    /**
     * 显示错误信息
     * @param context  上下文
     * @param mInfo  提示信息
     * @return
     */
    public Dialog showErrorDialog(Context context, String mInfo) {
        return showErrorDialog(context, null, mInfo, true , null,true , null);
    }
    /**
     * 显示错误信息
     * @param context  上下文
     * @param mTitle  标题
     * @param mInfo  提示信息
     * @param haveButton  是否有按钮
     * @param effectstype  动画效果  Effectstype.Fadein(默认效果)  Effectstype.Slideleft(左侧划入)  Effectstype.Slidetop(上部划入)   Effectstype.SlideBottom(底部划入)   Effectstype.Slideright(右侧划入)   Effectstype.Fall(缩放)
     *                     Effectstype.Newspager(左右旋转)   Effectstype.Fliph(上下翻转)  Effectstype.RotateBottom(从下部翻转划入)  Effectstype.RotateLeft(从左侧翻转划入)   Effectstype.Slit(翻转)  Effectstype.Shake(抖动)  Effectstype.Shake(Sidefill)
     * @param isCancelableOnTouchOutside 点击dialog外部是否消失
     * @param confirmListener  按钮点击事件
     * @return
     */
    public Dialog showErrorDialog(Context context, String mTitle, String mInfo, boolean haveButton,Effectstype effectstype ,Boolean isCancelableOnTouchOutside , View.OnClickListener confirmListener) {
        return showDialog(context, null, mTitle, mInfo, DialogTypeEnum.ERROR, haveButton, effectstype == null ? Effectstype.Shake : effectstype , isCancelableOnTouchOutside , confirmListener, null);
    }
    /**
     * 显示错误信息
     * @param context  上下文
     * @param mTitle  标题
     * @param mInfo  提示信息
     * @param haveButton  是否有按钮
     * @param confirmListener  按钮点击事件
     * @return
     */
    public Dialog showErrorDialog(Context context, String mTitle, String mInfo, boolean haveButton,View.OnClickListener confirmListener) {
        return showErrorDialog(context, mTitle, mInfo, haveButton, null ,true ,  confirmListener);
    }

    /**
     * 显示判定信息
     * @param context  上下文
     * @param mInfo  提示信息
     * @return
     */
    public Dialog showJudgeDialog(Context context, String mInfo) {
        return showJudgeDialog(context, null, mInfo, true, null, null);
    }
    /**
     * 显示判定信息
     * @param context  上下文
     * @param mTitle  标题
     * @param mInfo  提示信息
     * @param haveButton  是否有按钮
     * @param confirmListener  确定按钮点击事件
     * @param cancelListener  取消按钮点击事件
     * @return
     */
    public Dialog showJudgeDialog(Context context, String mTitle, String mInfo, boolean haveButton, View.OnClickListener confirmListener, View.OnClickListener cancelListener) {
        return showDialog(context, null, mTitle, mInfo, DialogTypeEnum.JUDGE, haveButton, null ,  false , confirmListener,cancelListener);
    }

    /**
     * dialog基础方法
     * @param mContext  上下文
     * @param mIcon  图标
     * @param mTitle  标题
     * @param mInfo  内容
     * @param dialogTypeEnum  按钮类型 INFO(提示信息)  WARMING(警告信息)  ERROR(错误信息)  JUDGE(判定信息)
     * @param haveButton  是否有按钮
     * @param effectstype  动画效果  Effectstype.Fadein(默认效果)  Effectstype.Slideleft(左侧划入)  Effectstype.Slidetop(上部划入)   Effectstype.SlideBottom(底部划入)   Effectstype.Slideright(右侧划入)   Effectstype.Fall(缩放)
     *                     Effectstype.Newspager(左右旋转)   Effectstype.Fliph(上下翻转)  Effectstype.RotateBottom(从下部翻转划入)  Effectstype.RotateLeft(从左侧翻转划入)   Effectstype.Slit(翻转)  Effectstype.Shake(抖动)  Effectstype.Shake(Sidefill)
     * @param isCancelableOnTouchOutside 点击dialog外部是否消失
     * @param confirmListener  确定按钮点击事件
     * @param cancelListener  取消按钮点击事件
     * @return
     */
    private Dialog showDialog(Context mContext, Bitmap mIcon, String mTitle, String mInfo, DialogTypeEnum dialogTypeEnum, boolean haveButton, Effectstype effectstype , Boolean isCancelableOnTouchOutside ,View.OnClickListener confirmListener, View.OnClickListener cancelListener) {
        if (mContext == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS , DialogConstants.EXCEPTION_INFO);
        }
        if (effectstype == null){
            effectstype = Effectstype.Fadein ;
        }
        Boolean mIsCancelableOnTouchOutside = isCancelableOnTouchOutside == null ?  true : isCancelableOnTouchOutside;
        dialogBuilder = NiftyDialogBuilder.getInstance(mContext);
        View niftyDialogEffectsView = LayoutInflater.from(mContext).inflate(R.layout.dialog_nifty_base, null);
        RelativeLayout head = niftyDialogEffectsView.findViewById(R.id.head);
        RelativeLayout headWithTitle = niftyDialogEffectsView.findViewById(R.id.headWithTitle);
        LinearLayout content = niftyDialogEffectsView.findViewById(R.id.content);
        LinearLayout foot = niftyDialogEffectsView.findViewById(R.id.foot);
        ImageView icon = niftyDialogEffectsView.findViewById(R.id.icon);
        ImageView iconWithTitle = niftyDialogEffectsView.findViewById(R.id.iconWithTitle);
        TextView title = niftyDialogEffectsView.findViewById(R.id.title);
        TextView info = niftyDialogEffectsView.findViewById(R.id.info);
        Button confirm = niftyDialogEffectsView.findViewById(R.id.confirm);
        Button cancel = niftyDialogEffectsView.findViewById(R.id.cancel);
        switch (dialogTypeEnum) {
            case INFO:
                if (mIcon == null) {
                    mIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ico_info);
                }
                break;
            case WARMING:
                if (mIcon == null) {
                    mIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ico_warning);
                }
//                effectstype = Effectstype.Shake;
                break;
            case ERROR:
                if (mIcon == null) {
                    mIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ico_error);
                }
//                effectstype = Effectstype.Shake;
                break;
            case JUDGE:
                if (mIcon == null) {
                    mIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ico_judge);
                }
                cancel.setVisibility(View.VISIBLE);
                cancel.setText(mContext.getResources().getString(R.string.tips_dialog_cancel));
                break;
        }
        confirm.setVisibility(View.VISIBLE);
        confirm.setText(mContext.getResources().getString(R.string.tips_dialog_sure));

        //判断是否有标题
        if (TextUtils.isEmpty(mTitle)) {
            headWithTitle.setVisibility(View.GONE);
            head.setVisibility(View.VISIBLE);
            icon.setImageBitmap(mIcon);
            iconWithTitle.setWillNotDraw(true);
        } else {
            headWithTitle.setVisibility(View.VISIBLE);
            head.setVisibility(View.GONE);
            icon.setWillNotDraw(true);
            iconWithTitle.setImageBitmap(mIcon);
            //有标题时不显示图标，代码未删除仅做隐藏处理
            iconWithTitle.setVisibility(View.GONE);
            title.setText(mTitle);
        }

        //判断是否有内容
        if (TextUtils.isEmpty(mInfo)) {
            content.setVisibility(View.GONE);
        } else {
            content.setVisibility(View.VISIBLE);
            info.setText(mInfo);
        }

        //判断是否有按钮
        if (haveButton) {
            foot.setVisibility(View.VISIBLE);
        } else {
            foot.setVisibility(View.GONE);
        }

        if (confirmListener == null) {
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.cancel();
                }
            });
        } else {
            confirm.setOnClickListener(confirmListener);
        }

        if (cancelListener == null) {
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.cancel();
                }
            });
        } else {
            cancel.setOnClickListener(cancelListener);
        }
        return showDialog(mContext , niftyDialogEffectsView , mIsCancelableOnTouchOutside , effectstype);
    }
    /**
     * dialog基础方法
     * @param mContext  上下文
     * @param view dialog布局
     * @param isCancelableOnTouchOutside 点击dialog外部是否消失
     * @param effectstype  动画效果  Effectstype.Fadein(默认效果)  Effectstype.Slideleft(左侧划入)  Effectstype.Slidetop(上部划入)   Effectstype.SlideBottom(底部划入)   Effectstype.Slideright(右侧划入)   Effectstype.Fall(缩放)
     *                     Effectstype.Newspager(左右旋转)   Effectstype.Fliph(上下翻转)  Effectstype.RotateBottom(从下部翻转划入)  Effectstype.RotateLeft(从左侧翻转划入)   Effectstype.Slit(翻转)  Effectstype.Shake(抖动)  Effectstype.Shake(Sidefill)
'     * @return
     */
    private Dialog showDialog(Context mContext, View view , Boolean isCancelableOnTouchOutside ,Effectstype effectstype) {
        if (mContext == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS , DialogConstants.EXCEPTION_INFO);
        }
        if (effectstype == null){
            effectstype = Effectstype.Fadein ;
        }
        dialogBuilder = NiftyDialogBuilder.getInstance(mContext);
        dialogBuilder
                .withTitle(null)
                .withMessage(null)
                .isCancelableOnTouchOutside(isCancelableOnTouchOutside)
                .withDuration(DialogConstants.DURATION)
                .withEffect(effectstype)
                .setCustomView(view, mContext);
        LinearLayout linearLayout = dialogBuilder.findViewById(R.id.parentPanel);
        linearLayout.setBackgroundResource(R.drawable.dialog_corners);
        dialogBuilder.show();
        return dialogBuilder;
    }
}
