package com.inossem.app.device;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.app.device.util.DeviceUtils;
import com.inossem_library.tips.toast.util.ToastUtils;

/**
 * 设备相关
 *
 * @author LinH
 */
public class DeviceActivity extends BaseActivity {
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        description = findViewById(R.id.description);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(DeviceActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(final Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("判断设备是否ROOT");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean deviceRooted = DeviceUtils.isDeviceRooted();
                                if (deviceRooted) {
                                    ToastUtils.show(DeviceActivity.this, "已经ROOT");
                                } else {
                                    ToastUtils.show(DeviceActivity.this, "尚未ROOT");
                                }
                            }
                        });
                        break;
                    case 1:
                        button.setText("判断设备ADB是否可用");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean adbEnabled = DeviceUtils.isAdbEnabled(DeviceActivity.this);
                                if (adbEnabled) {
                                    ToastUtils.show(DeviceActivity.this, "ADB可用");
                                } else {
                                    ToastUtils.show(DeviceActivity.this, "ADB不可用");
                                }
                            }
                        });
                        break;
                    case 2:
                        button.setText("获取设备系统版本号");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String sdkVersionName = DeviceUtils.getSDKVersionName();
                                ToastUtils.show(DeviceActivity.this, sdkVersionName);
                            }
                        });
                        break;
                    case 3:
                        button.setText("获取设备系统版本码");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int sdkVersionCode = DeviceUtils.getSDKVersionCode();
                                ToastUtils.show(DeviceActivity.this, String.valueOf(sdkVersionCode));
                            }
                        });
                        break;
                    case 4:
                        button.setText("获取设备 AndroidID");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String androidID = DeviceUtils.getAndroidID(DeviceActivity.this);
                                ToastUtils.show(DeviceActivity.this, androidID);
                            }
                        });
                        break;
                    case 5:
                        button.setText("获取设备 MAC 地址");
                        button.setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            @Override
                            public void onClick(View v) {
                                try {
                                    String macAddress = DeviceUtils.getMacAddress(DeviceActivity.this);
                                    ToastUtils.show(DeviceActivity.this, macAddress);
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 6:
                        button.setText("获取设备厂商");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String manufacturer = DeviceUtils.getManufacturer();
                                ToastUtils.show(DeviceActivity.this, manufacturer);
                            }
                        });
                        break;
                    case 7:
                        button.setText("获取设备型号");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String model = DeviceUtils.getModel();
                                ToastUtils.show(DeviceActivity.this, model);
                            }
                        });
                        break;
                    case 8:
                        button.setText("获取设备ABIs(CPU)");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String[] abIs = DeviceUtils.getABIs();
                                for (String abI : abIs) {
                                    ToastUtils.show(DeviceActivity.this, abI);
                                }
                            }
                        });
                        break;
                    case 9:
                        button.setText("判断是否是平板");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean tablet = DeviceUtils.isTablet(DeviceActivity.this);
                                if (tablet) {
                                    ToastUtils.show(DeviceActivity.this, "是");
                                } else {
                                    ToastUtils.show(DeviceActivity.this, "否");
                                }
                            }
                        });
                        break;
                    case 10:
                        button.setText("判断是否是模拟器");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean tablet = DeviceUtils.isEmulator(DeviceActivity.this);
                                if (tablet) {
                                    ToastUtils.show(DeviceActivity.this, "是");
                                } else {
                                    ToastUtils.show(DeviceActivity.this, "否");
                                }
                            }
                        });
                        break;
                }
            }
        });
    }
}
