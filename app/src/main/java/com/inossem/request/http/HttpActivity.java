package com.inossem.request.http;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.TypeAdapter;
import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.request.http.api.LogInApiService;
import com.inossem.request.http.bean.BaseBean;
import com.inossem.request.http.bean.LogInBean;
import com.inossem.request.http.bean.LogInRequestBean;
import com.inossem.util.Utils;
import com.inossem_library.request.http.constant.RetrofitCallBackError;
import com.inossem_library.request.http.constant.RetrofitConstant;
import com.inossem_library.request.http.util.RetrofitCallback;
import com.inossem_library.request.http.util.RetrofitManager;
import com.inossem_library.request.http.util.RetrofitUtils;
import com.inossem_library.other.compress.util.GZIPUtils;
import com.inossem_library.request.http.util.dealWithData.InossemRequestConverterListener;
import com.inossem_library.request.http.util.dealWithData.InossemResponseConverterListener;
import com.inossem_library.tips.toast.util.ToastUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe Http请求 Demo
 */
public class HttpActivity extends BaseActivity {

    private TextView description;

    private LinearLayout buttonLayout;

    private RetrofitManager retrofitManager;

    @Override
    protected void onCreateImpl(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofitManager = new RetrofitManager();

        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);

        description.setText("Http");

        Utils.createButtons(HttpActivity.this, buttonLayout, 3, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("普通请求");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestCommon(false);
                            }
                        });
                        break;
                    case 1:
                        button.setText("对数据进行处理");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestDealWithData();
                            }
                        });
                        break;
                    case 2:
                        button.setText("请求带Dialog(Retrofit不参与UI封装, Dialog需自定义)");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestCommon(true);
                            }
                        });
                        break;
                }
            }
        });
    }

    public void call(Call call, Callback callback) {
        retrofitManager.call(call, callback);
    }

    @Override
    protected void onDestroyImpl() {
        super.onDestroy();
        retrofitManager.cancelAll();
    }

    /**
     * 请求Demo(普通请求)
     */
    private void requestCommon(Boolean isHaveDialog) {
        /*
        初始化一些请求参数
         */
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("lang_code", "zh-CN");
        header.put("RS", "1");
        header.put("Authorization", "");
        try {
            RetrofitUtils.set(this, "https://www.wanandroid.com/", header, 20_000L, 20_000L, 20_000L, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        开始请求
         */
        LogInRequestBean requestBean = new LogInRequestBean();
        requestBean.setPassword("123456");
        requestBean.setUsername("zjy");

        try {
            call(RetrofitUtils.getRetrofit(HttpActivity.this, "詹建宇", "詹建宇", "HttpActivity", "测试").create(LogInApiService.class)
                            .loginCommon(requestBean),
                    isHaveDialog ?
                            //当传入自定义的Dialog, 请求时会自动显示、消失
                            new RetrofitCallback<BaseBean<LogInBean>>(new ProgressDialog(HttpActivity.this)) {
                                @Override
                                public void success(Response<BaseBean<LogInBean>> response) {
                                    ToastUtils.show(HttpActivity.this, response.body().getErrorMsg());
                                }

                                @Override
                                public void httpError(Response<BaseBean<LogInBean>> response) {

                                }

                                @Override
                                public void failure(RetrofitCallBackError error) {

                                }
                            }
                            :
                            //当不传Dialog, 则不会显示
                            new RetrofitCallback<BaseBean<LogInBean>>() {
                                @Override
                                public void success(Response<BaseBean<LogInBean>> response) {
                                    ToastUtils.show(HttpActivity.this, response.body().getErrorMsg());
                                }

                                @Override
                                public void httpError(Response<BaseBean<LogInBean>> response) {

                                }

                                @Override
                                public void failure(RetrofitCallBackError error) {

                                }
                            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求Demo(对数据进行处理)
     */
    private void requestDealWithData() {
        /*
        初始化一些请求参数
         */
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("lang_code", "zh-CN");
        header.put("RS", "1");
        header.put("Authorization", "");
        try {
            RetrofitUtils.set(this, "http://192.168.3.102:8080/HttpTest/", header, 20_000L, 20_000L, 20_000L, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        开始请求
         */
        LogInRequestBean requestBean = new LogInRequestBean();
        requestBean.setPassword("123456");
        requestBean.setUsername("123456");

        try {
            call(RetrofitUtils.getRetrofit(HttpActivity.this, "詹建宇", "詹建宇", "HttpActivity", "测试",
                    new InossemRequestConverterListener<LogInRequestBean>() {
                        @Override
                        public RequestBody onListener(@NonNull LogInRequestBean o, TypeAdapter<LogInRequestBean> adapter) throws IOException {
                            //处理请求
                            String utfStr = new String(adapter.toJson(o).getBytes(RetrofitConstant.CHARSET_CAPITAL_UTF8), RetrofitConstant.CHARSET_CAPITAL_ISO88591);
                            byte[] isoByte = utfStr.getBytes(RetrofitConstant.CHARSET_CAPITAL_ISO88591);
                            return RequestBody.create(MediaType.parse("application/json;charset=" + RetrofitConstant.CHARSET_CAPITAL_ISO88591), GZIPUtils.compress(isoByte));
                        }
                    }, new InossemResponseConverterListener<BaseBean<LogInBean>>() {
                        @Override
                        public BaseBean<LogInBean> onListener(@NonNull ResponseBody responseBody, TypeAdapter<BaseBean<LogInBean>> adapter) throws IOException {
                            //处理响应
                            byte[] isoByte = responseBody.bytes();
                            byte[] utfByte = GZIPUtils.uncompress(isoByte);
                            String result = new String(utfByte, "UTF-8");
                            return adapter.fromJson(result);
                        }
                    }).create(LogInApiService.class)
                            .loginDealWithData(requestBean),
                    new RetrofitCallback<BaseBean<LogInBean>>() {
                        @Override
                        public void success(Response<BaseBean<LogInBean>> response) {
                            ToastUtils.show(HttpActivity.this, response.body().getErrorMsg());
                        }

                        @Override
                        public void httpError(Response<BaseBean<LogInBean>> response) {

                        }

                        @Override
                        public void failure(RetrofitCallBackError error) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
