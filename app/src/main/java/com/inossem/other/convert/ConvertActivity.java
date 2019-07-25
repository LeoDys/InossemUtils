package com.inossem.other.convert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.other.OtherActivity;
import com.inossem.other.push.PushActivity;
import com.inossem.util.Utils;
import com.inossem_library.other.convert.constant.ConvertConstants;
import com.inossem_library.other.convert.util.ConvertUtils;
/**
 * @author 郭晓龙
 * @time on 2019/7/25
 * @email xiaolong.guo@inossem.com
 * @describe 转换
 */
public class ConvertActivity extends BaseActivity {

    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(ConvertActivity.this, buttonLayout, 1, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("转换");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("TAG", "bytes2Bits-->" + ConvertUtils.bytes2Bits(new byte[]{(byte) 0x31, (byte) 0x32, (byte) 0x33}));
                                Log.e("TAG", "bits2Bytes-->" + new String(ConvertUtils.bits2Bytes("  ")));
                                Log.e("TAG", "bits2Bytes-->" + new String(ConvertUtils.bits2Bytes("001100010011001000110011")));
                                Log.e("TAG", "bytes2Chars-->" + new String(ConvertUtils.bytes2Chars(new byte[]{(byte) 0x31, (byte) 0x32, (byte) 0x33})));
                                Log.e("TAG", "chars2Bytes-->" + new String(ConvertUtils.chars2Bytes(new char[]{'1', '2', '3'})));
                                Log.e("TAG", "bytes2HexString-->" + ConvertUtils.bytes2HexString("123".getBytes()));
                                Log.e("TAG", "hexString2Bytes-->" + new String(ConvertUtils.hexString2Bytes("313233")));
                                Log.e("TAG", "memorySize2Byte-->" + ConvertUtils.memorySize2Byte(10, ConvertConstants.MB) + "");
                                Log.e("TAG", "byte2MemorySize-->" + ConvertUtils.byte2MemorySize(10000, ConvertConstants.KB) + "");
                                Log.e("TAG", "byte2FitMemorySize-->" + ConvertUtils.byte2FitMemorySize(10000) + "");
                            }
                        });
                        break;
                }
            }
        });
    }
}
