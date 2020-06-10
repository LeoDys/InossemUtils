package com.inossem_library.request.http.util.gsonadapter;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.inossem_library.request.http.constant.RetrofitLogConstant;

import java.io.IOException;

public class StringNullAdapter extends TypeAdapter<String> {
    @Override
    public String read(JsonReader reader) throws IOException {
        String name = reader.toString();
        if (reader.peek() == JsonToken.NULL) {
            Log.e(RetrofitLogConstant.JSON_PARSE_ERROR, "字段:" + name + " is null");
            reader.skipValue(); //跳过当前
            return "";
        }
        return reader.nextString();
    }

    @Override
    public void write(JsonWriter writer, String value) throws IOException {
        writer.value(value);
    }
}