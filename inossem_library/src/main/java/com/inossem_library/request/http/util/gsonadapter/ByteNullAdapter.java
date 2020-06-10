package com.inossem_library.request.http.util.gsonadapter;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.inossem_library.request.http.constant.RetrofitLogConstant;

import java.io.IOException;

public class ByteNullAdapter extends TypeAdapter<Byte> {
    @Override
    public Byte read(JsonReader reader) throws IOException {

        String name = reader.toString();
        if (reader.peek() == JsonToken.STRING) {
            Byte aByte;
            try {
                aByte = new Byte(reader.nextString());
            } catch (Exception e) {
                reader.skipValue(); // 跳过当前
                Log.e(RetrofitLogConstant.JSON_PARSE_ERROR, "字段:" + name + " value error:" + reader.nextString());
                aByte = 99;
            }
            return aByte;
        }
        if (reader.peek() == JsonToken.NULL) {
            Log.e(RetrofitLogConstant.JSON_PARSE_ERROR, "字段:" + name + " is null");
            reader.skipValue(); // 跳过当前
            return -1;
        }

        if (reader.peek() == JsonToken.NUMBER) {
            return new Byte(String.valueOf((int) reader.nextDouble()));
        }

        Integer b = new Integer(reader.nextInt());
        return b.byteValue();
    }

    @Override
    public void write(JsonWriter writer, Byte value) throws IOException {
        writer.value(value);
    }
}