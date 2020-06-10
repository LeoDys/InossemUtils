package com.inossem_library.request.http.util.gsonadapter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;

public class MineTypeAdapterFactory<T> implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType == Float.class || rawType == float.class) {// null ——> -1.0
            // "" ——> 0.0
            // "123" ——> 123
            // "啊啊aa" ——> -1.0
            return (TypeAdapter<T>) new FloatlNullAdapter();
        } else if (rawType == Integer.class || rawType == int.class) {
            // null ——> 0
            // "" ——> -1
            // "123" ——> 123
            // "啊啊aa" ——> -1
            return (TypeAdapter<T>) new IntNullAdapter();
        } else if (rawType == Byte.class || rawType == byte.class) {
            /**
             * -128到+127
             */
            // null ——> -1
            // "" ——> -1
            // "123" ——> 123
            // 小于-129 ——> -128
            // 大于128 ——> 127
            // "啊啊aa" ——> -1
            return (TypeAdapter<T>) new ByteNullAdapter();
        } else if (rawType == BigDecimal.class) {
            // null ——> 0.00
            // "" ——> -1.00
            // "123" ——> 123
            // "啊啊aa" ——> -1
            return (TypeAdapter<T>) new BigDecimalNullAdapter();
        } else if (rawType == Double.class || rawType == double.class) {
            return (TypeAdapter<T>) new DoublelNullAdapter();
        } else if (rawType == String.class) {
            // null ——> ""
            // "" ——> ""
            return (TypeAdapter<T>) new StringNullAdapter();
        }
        return null;
    }
}