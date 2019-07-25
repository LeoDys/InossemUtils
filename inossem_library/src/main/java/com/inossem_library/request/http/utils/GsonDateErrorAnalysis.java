package com.inossem_library.request.http.utils;

import android.annotation.SuppressLint;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.inossem_library.request.http.constant.RetrofitConstant;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe 处理Date转String的解析工具
 */
public class GsonDateErrorAnalysis implements JsonDeserializer<Date>, JsonSerializer<Date> {

    private GsonDateErrorAnalysis() {
    }

    //实例
    private static GsonDateErrorAnalysis instance;

    /**
     * 单例
     *
     * @return GsonDateErrorAnalysis实例
     */
    static synchronized GsonDateErrorAnalysis getInstance() {
        if (instance == null) {
            instance = new GsonDateErrorAnalysis();
        }
        return instance;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        /*
        反序列化
         */
        try {
            if (json.getAsString().equals("") || json.getAsString().equals("null")) {
                //定义为Date类型,如果后台返回""或者null,则返回null
                return null;
            } else {
                //如果时间超过10位的话 时间格式为"yyyy-MM-dd HH:mm:ss" 否则为"yyyy-MM-dd"
                return new SimpleDateFormat(json.getAsJsonPrimitive().getAsString().length() > 10 ? RetrofitConstant.FORMAT_LONG : RetrofitConstant.FORMAT_SHORT).parse(json.getAsJsonPrimitive().getAsString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        /*
        序列化
         */
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(RetrofitConstant.FORMAT_LONG);
        return new JsonPrimitive(simpleDateFormat.format(src));
    }

}
