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
import com.inossem_library.request.http.constant.RetrofitConstant;
import com.inossem_library.request.http.utils.RetrofitCallback;
import com.inossem_library.request.http.utils.RetrofitManager;
import com.inossem_library.request.http.utils.RetrofitUtils;
import com.inossem_library.request.http.utils.dealWithData.GZIPUtils;
import com.inossem_library.request.http.utils.dealWithData.InossemRequestConverterListener;
import com.inossem_library.request.http.utils.dealWithData.InossemResponseConverterListener;

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

        Utils.createButtons(HttpActivity.this, buttonLayout, 1, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("请求");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                request();
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
     * 请求Demo
     */
    private void request() {
        /*
        初始化一些请求参数
         */
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("lang_code", "zh-CN");
        header.put("RS", "1");
        header.put("Authorization", "");

        try {
            RetrofitUtils.set(this, "http://192.168.3.102:8080/HttpTest/", header, 20_000L, 20_000L, 20_000L);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        开始请求
         */
        LogInRequestBean requestBean = new LogInRequestBean();
        requestBean.setPassword("123456");
        requestBean.setLoginName("123456");

        try {
            call(RetrofitUtils.getRetrofit(HttpActivity.this, "詹建宇", "詹建宇", "HttpActivity", "测试",
                    new InossemRequestConverterListener<LogInRequestBean>() {
                        @Override
                        public RequestBody onListener(@NonNull LogInRequestBean o, TypeAdapter<LogInRequestBean> adapter) throws IOException {
                            String utfStr = new String(adapter.toJson(o).getBytes(RetrofitConstant.CHARSET_CAPITAL_UTF8), RetrofitConstant.CHARSET_CAPITAL_ISO88591);
                            byte[] isoByte = utfStr.getBytes(RetrofitConstant.CHARSET_CAPITAL_ISO88591);
                            return RequestBody.create(MediaType.parse("application/json;charset=" + RetrofitConstant.CHARSET_CAPITAL_ISO88591), GZIPUtils.compress(isoByte));
                        }
                    }, new InossemResponseConverterListener<BaseBean<LogInBean>>() {
                        @Override
                        public BaseBean<LogInBean> onListener(@NonNull ResponseBody responseBody, TypeAdapter<BaseBean<LogInBean>> adapter) throws IOException {
                            byte[] isoByte = responseBody.bytes();
                            byte[] utfByte = GZIPUtils.uncompress(isoByte);
                            String result = new String(utfByte, "UTF-8");
                            return adapter.fromJson(result);
                        }
                    }).create(LogInApiService.class)
                            .login(requestBean),
                    new RetrofitCallback<LogInBean>(new ProgressDialog(HttpActivity.this)) {
                        @Override
                        public void success(Response<LogInBean> response) {

                        }

                        @Override
                        public void httpError(Response<LogInBean> response) {

                        }

                        @Override
                        public void failure(Throwable t) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
