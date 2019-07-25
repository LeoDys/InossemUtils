package com.inossem.request.http.api;

import com.inossem.request.http.bean.BaseBean;
import com.inossem.request.http.bean.LogInBean;
import com.inossem.request.http.bean.LogInRequestBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe 登录接口
 */
public interface LogInApiService {

    @POST("TestServlet")
    Call<BaseBean<LogInBean>> login(@Body LogInRequestBean bean);

}
