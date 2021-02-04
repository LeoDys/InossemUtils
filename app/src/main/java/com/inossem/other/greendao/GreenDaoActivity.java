package com.inossem.other.greendao;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.greendao.Mat;
import com.inossem.greendao.WhCode;
import com.inossem.util.Utils;
import com.inossem_library.tips.toast.util.ToastUtils;

import java.util.List;

/**
 * @author 郭晓龙
 * @time on 2021/1/29
 * @email xiaolong.guo@inossem.com
 * @describe greenDao
 */
public class GreenDaoActivity extends BaseActivity {

    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(GreenDaoActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("Set Mat");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Mat mat = new Mat();
                                mat.setMatCode("1001");
                                DaoManager.getInstance().getManager(Mat.class.getSimpleName()).save(mat);

                            }
                        });
                        break;
                    case 1:
                        button.setText("Set WhCode");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                WhCode mat = new WhCode();
                                mat.setMatCode("1002");
                                DaoManager.getInstance().getManager(WhCode.class.getSimpleName()).save(mat);

                            }
                        });
                        break;
                    case 2:
                        button.setText("Get Mat");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                List<Mat> mat = DaoManager.getInstance().getManager(Mat.class.getSimpleName()).queryAll();
                                StringBuffer s = new StringBuffer();
                                for (Mat bean : mat) {
                                    s.append(bean.getMatCode() + ",");
                                }
                                ToastUtils.show(GreenDaoActivity.this, s.toString());
                            }
                        });
                        break;
                    case 3:
                        button.setText("Get WhCode");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                List<WhCode> mat = DaoManager.getInstance().getManager(WhCode.class.getSimpleName()).queryAll();
                                StringBuffer s = new StringBuffer();
                                for (WhCode bean : mat) {
                                    s.append(bean.getMatCode() + ",");
                                }
                                ToastUtils.show(GreenDaoActivity.this, s.toString());
                            }
                        });
                        break;
                    case 4:
                        button.setText("Clear All");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DaoManager.getInstance().deleSQL();
                            }
                        });
                        break;
                }
            }
        });
    }
}
