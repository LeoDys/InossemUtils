package com.inossem_library.request.http.util.gsonadapter;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.inossem_library.request.http.constant.RetrofitLogConstant;

import java.io.IOException;
import java.math.BigDecimal;

public class FloatlNullAdapter extends TypeAdapter<Float> {
    @Override
    public Float read(JsonReader reader) throws IOException {
        String name = reader.toString();
        if (reader.peek() == JsonToken.STRING) {
            float v;
            try {
                v = new BigDecimal(reader.nextString()).floatValue();
            } catch (Exception e) {
                Log.e(RetrofitLogConstant.JSON_PARSE_ERROR, "字段:" + name + " value error:" + reader.nextString());
                v = 9999999f;
            }
            return v;
        }
        if (reader.peek() == JsonToken.NULL) {
            Log.e(RetrofitLogConstant.JSON_PARSE_ERROR, "字段:" + name + " is null");
            reader.skipValue(); //跳过当前
            return 0.0f;
        }
        BigDecimal bigDecimal = new BigDecimal(reader.nextString());
        return bigDecimal.floatValue();
    }

    @Override
    public void write(JsonWriter writer, Float value) throws IOException {
        writer.value(value);
    }
}