package com.inossem.other.permission;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.other.convert.constant.ConvertConstants;
import com.inossem_library.other.convert.util.ConvertUtils;
import com.inossem_library.other.permission.util.RequestApplicationDangerPermissonsUtils;
import com.inossem_library.tips.logcat.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限检测
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/7/26 9:37
 * @version Java-1.8
 * @since 1.0.0
 */
public class PermissionActivity extends BaseActivity {

    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(PermissionActivity.this, buttonLayout, 10, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("获取应用在Manifest中申请的危险权限");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                List<String> manifestDangerPermission = RequestApplicationDangerPermissonsUtils.getManifestDangerPermission(PermissionActivity.this
                                        , "com.inossem");
                                LogUtils.e(manifestDangerPermission.toString());
                            }
                        });
                        break;
                    case 1:
                        button.setText("获取哪些权限没有获取");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                List<String> manifestDangerPermission = RequestApplicationDangerPermissonsUtils.noHavePermission(PermissionActivity.this
                                        , "com.inossem", RequestApplicationDangerPermissonsUtils.getManifestDangerPermission(PermissionActivity.this
                                                , "com.inossem"));
                                LogUtils.e(manifestDangerPermission.toString());
                            }
                        });
                        break;
                }
            }
        });
    }
}
