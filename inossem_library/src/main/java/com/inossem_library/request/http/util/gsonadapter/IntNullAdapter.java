package com.inossem_library.request.http.util.gsonadapter;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.inossem_library.request.http.constant.RetrofitLogConstant;

import java.io.IOException;
import java.math.BigDecimal;

public class IntNullAdapter extends TypeAdapter<Integer> {
    @Override
    public Integer read(JsonReader reader) throws IOException {
        String name = reader.toString();
        if (reader.peek() == JsonToken.STRING) {
            reader.skipValue(); //跳过当前
            Integer integer;
            try {
                integer = new Integer(reader.nextString());
            } catch (Exception e) {
                Log.e(RetrofitLogConstant.JSON_PARSE_ERROR, "字段:" + name + " value error:" + reader.nextString());
                integer = 9999999;
            }
            return integer;
        }
        if (reader.peek() == JsonToken.NULL) {
            Log.e(RetrofitLogConstant.JSON_PARSE_ERROR, "字段:" + name + " is null");
            reader.skipValue(); //跳过当前
            return 0;
        }
        return reader.nextInt();
    }

    @Override
    public void write(JsonWriter writer, Integer value) throws IOException {
        writer.value(value);
    }
}