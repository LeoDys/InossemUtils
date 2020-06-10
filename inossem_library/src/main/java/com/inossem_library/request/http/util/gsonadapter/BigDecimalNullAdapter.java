package com.inossem_library.request.http.util.gsonadapter;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.inossem_library.request.http.constant.RetrofitLogConstant;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalNullAdapter extends TypeAdapter<BigDecimal> {
    @Override
    public BigDecimal read(JsonReader reader) throws IOException {
        String name = reader.toString();
        if (reader.peek() == JsonToken.NULL) {
            Log.e(RetrofitLogConstant.JSON_PARSE_ERROR, "字段:" + name + " is null");
            reader.skipValue(); // 跳过当前
            return new BigDecimal("0.00");
        }
        if (reader.peek() == JsonToken.STRING) {
            BigDecimal bigDecimal;
            try {
                bigDecimal = new BigDecimal(reader.nextString());
            } catch (Exception e) {
                Log.e(RetrofitLogConstant.JSON_PARSE_ERROR, "字段:" + name + "value error:" + reader.nextString());
                reader.skipValue(); // 跳过当前
                bigDecimal = new BigDecimal("9999999.00");
            }
            return bigDecimal;
        }
        BigDecimal bigDecimal = new BigDecimal(reader.nextString());
        return bigDecimal;
    }

    @Override
    public void write(JsonWriter writer, BigDecimal value) throws IOException {
        writer.value(value);
    }
}