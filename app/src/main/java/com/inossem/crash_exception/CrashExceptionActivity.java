package com.inossem.crash_exception;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.app.path.util.PathUtils;
import com.inossem_library.writelogs.constant.LogWriteConstant;

import java.io.File;

import androidx.core.content.FileProvider;

/**
 * @author 郭晓龙
 * @time on 2020-12-25 10:53:47
 * @email xiaolong.guo@inossem.com
 * @describe 异常捕获
 */
public class CrashExceptionActivity extends BaseActivity {

    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreateImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(CrashExceptionActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("崩溃");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int a = 0 / 0;
                            }
                        });
                        break;
                    case 1:
                        button.setText("查看奔溃日志");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    Uri uri = FileProvider.getUriForFile(CrashExceptionActivity.this, CrashExceptionActivity.this.getPackageName() + ".fileprovider", new File(PathUtils.getLegalPath(CrashExceptionActivity.this, Environment.DIRECTORY_DOCUMENTS)
                                            + File.separator + LogWriteConstant.LOG_ROOT_PATH + File.separator + LogWriteConstant.LOG_NAME_CRASHEXCEPTION));
                                    intent.setDataAndType(uri, "file/*");
                                } else {
                                    intent.setDataAndType(Uri.fromFile(new File(PathUtils.getLegalPath(CrashExceptionActivity.this, Environment.DIRECTORY_DOCUMENTS)
                                            + File.separator + LogWriteConstant.LOG_ROOT_PATH + File.separator + LogWriteConstant.LOG_NAME_CRASHEXCEPTION)), "file/*");
                                }
                                startActivity(intent);
                            }
                        });
                        break;
                }
            }
        });
    }
}
