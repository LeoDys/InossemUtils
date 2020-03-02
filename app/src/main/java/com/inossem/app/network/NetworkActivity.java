package com.inossem.app.network;

import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.app.network.constant.NetworkTypeEnum;
import com.inossem_library.app.network.util.NetworkUtils;
import com.inossem_library.tips.toast.util.ToastUtils;

/**
 * 网络相关
 *
 * @author LinH
 */
public class NetworkActivity extends AppCompatActivity {
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        description = findViewById(R.id.description);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(NetworkActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(final Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("打开网络设置界面");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                NetworkUtils.openWirelessSettings(NetworkActivity.this);
                            }
                        });
                        break;
                    case 1:
                        button.setText("判断网络是否连接");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean connected = NetworkUtils.isConnected(NetworkActivity.this);
                                if (connected) {
                                    ToastUtils.show(NetworkActivity.this, "连接");
                                } else {
                                    ToastUtils.show(NetworkActivity.this, "未连接");
                                }
                            }
                        });
                        break;
                    case 2:
                        button.setText("判断移动数据是否打开");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    boolean mobileDataEnabled = NetworkUtils.getMobileDataEnabled(NetworkActivity.this);
                                    if (mobileDataEnabled) {
                                        ToastUtils.show(NetworkActivity.this, "打开");
                                    } else {
                                        ToastUtils.show(NetworkActivity.this, "未打开");
                                    }
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 3:
                        button.setText("判断网络是否是移动数据");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean mobileDataEnabled = NetworkUtils.isMobileData(NetworkActivity.this);
                                if (mobileDataEnabled) {
                                    ToastUtils.show(NetworkActivity.this, "移动数据");
                                } else {
                                    ToastUtils.show(NetworkActivity.this, "非移动数据");
                                }
                            }
                        });
                        break;
                    case 4:
                        button.setText("判断网络是否是4G");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean mobileDataEnabled = NetworkUtils.is4G(NetworkActivity.this);
                                if (mobileDataEnabled) {
                                    ToastUtils.show(NetworkActivity.this, "4G");
                                } else {
                                    ToastUtils.show(NetworkActivity.this, "非4G");
                                }
                            }
                        });
                        break;
                    case 5:
                        button.setText("判断WIFI是否打开");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean mobileDataEnabled = NetworkUtils.getWifiEnabled(NetworkActivity.this);
                                if (mobileDataEnabled) {
                                    ToastUtils.show(NetworkActivity.this, "打开");
                                } else {
                                    ToastUtils.show(NetworkActivity.this, "未打开");
                                }
                            }
                        });
                        break;
                    case 6:
                        button.setText("更改WIFI状态");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean isWifiEnabled = NetworkUtils.getWifiEnabled(NetworkActivity.this);
                                if (!isWifiEnabled) {
                                    NetworkUtils.setWifiEnabled(NetworkActivity.this, true);
                                    ToastUtils.show(NetworkActivity.this, "打开");
                                    isWifiEnabled = true;
                                } else {
                                    NetworkUtils.setWifiEnabled(NetworkActivity.this, false);
                                    ToastUtils.show(NetworkActivity.this, "关闭");
                                    isWifiEnabled = false;
                                }
                            }
                        });
                        break;
                    case 7:
                        button.setText("判断WIFI是否连接状态");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean wifiConnected = NetworkUtils.isWifiConnected(NetworkActivity.this);
                                if (wifiConnected) {
                                    ToastUtils.show(NetworkActivity.this, "连接");
                                } else {
                                    ToastUtils.show(NetworkActivity.this, "未连接");
                                }
                            }
                        });
                        break;
                    case 8:
                        button.setText("获取移动网络运营商名称");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String networkOperatorName = NetworkUtils.getNetworkOperatorName(NetworkActivity.this);
                                ToastUtils.show(NetworkActivity.this, networkOperatorName);
                            }
                        });
                        break;
                    case 9:
                        button.setText("获取当前网络类型");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                NetworkTypeEnum networkType = NetworkUtils.getNetworkType(NetworkActivity.this);
                                ToastUtils.show(NetworkActivity.this, String.valueOf(networkType));
                            }
                        });
                        break;
                    case 10:
                        button.setText("是否使用以太网");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean ethernet = NetworkUtils.isEthernet(NetworkActivity.this);
                                if (ethernet) {
                                    ToastUtils.show(NetworkActivity.this, "以太网");
                                } else {
                                    ToastUtils.show(NetworkActivity.this, "非以太网");
                                }
                            }
                        });
                        break;
                    case 11:
                        button.setText("获取网络信息");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                NetworkInfo activeNetworkInfo = NetworkUtils.getActiveNetworkInfo(NetworkActivity.this);
                                ToastUtils.show(NetworkActivity.this, String.valueOf(activeNetworkInfo));
                            }
                        });
                        break;
                    case 12:
                        button.setText("获取IP地址");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String ipAddress = NetworkUtils.getIPAddress(true);
                                    ToastUtils.show(NetworkActivity.this, String.valueOf(ipAddress));
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 13:
                        button.setText("根据WIFI获取网络IP地址");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String ipAddressByWifi = NetworkUtils.getIpAddressByWifi(NetworkActivity.this);
                                ToastUtils.show(NetworkActivity.this, ipAddressByWifi);
                            }
                        });
                        break;
                    case 14:
                        button.setText("根据WIFI获取网关");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String gatewayByWifi = NetworkUtils.getGatewayByWifi(NetworkActivity.this);
                                ToastUtils.show(NetworkActivity.this, gatewayByWifi);
                            }
                        });
                        break;
                    case 15:
                        button.setText("根据WIFI获取子网掩码");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String gatewayByWifi = NetworkUtils.getNetMaskByWifi(NetworkActivity.this);
                                ToastUtils.show(NetworkActivity.this, gatewayByWifi);
                            }
                        });
                        break;
                    case 16:
                        button.setText("根据WIFI获取服务端(DHCP服务器)");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String serverAddressByWifi = NetworkUtils.getServerAddressByWifi(NetworkActivity.this);
                                ToastUtils.show(NetworkActivity.this, serverAddressByWifi);
                            }
                        });
                        break;
                }
            }
        });
    }
}