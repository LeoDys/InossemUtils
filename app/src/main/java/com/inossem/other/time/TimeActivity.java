package com.inossem.other.time;

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
import com.inossem.other.string.StringActivity;
import com.inossem.util.Utils;
import com.inossem_library.other.convert.constant.ConvertConstants;
import com.inossem_library.other.convert.util.ConvertUtils;
import com.inossem_library.other.push.constant.PushConstant;
import com.inossem_library.other.push.util.PushUtils;
import com.inossem_library.other.time.constant.TimeConstants;
import com.inossem_library.other.time.util.TimeUtils;

import java.text.ParseException;
import java.util.Calendar;
/**
 * @author 郭晓龙
 * @time on 2019/7/25
 * @email xiaolong.guo@inossem.com
 * @describe Time
 */
public class TimeActivity extends BaseActivity {

    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(TimeActivity.this, buttonLayout, 1, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("时间");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("TAG", "millis2String-->" + TimeUtils.millis2String(System.currentTimeMillis()) + "");
                                try {
                                    Log.e("TAG", "string2Millis-->" + TimeUtils.string2Millis("2019-07-25 11:11:11") + "");
                                    Log.e("TAG", "getTimeSpan-->" + TimeUtils.getTimeSpan("2019-07-25 11:11:11", "2019-07-26 11:11:11", TimeConstants.HOUR) + "");
                                    Log.e("TAG", "getFitTimeSpan-->" + TimeUtils.getFitTimeSpan(getApplicationContext(), "2019-07-25 11:11:11", "2019-07-26 12:11:11", 2) + "");
                                    Log.e("TAG", "getTimeSpanByNow-->" + TimeUtils.getTimeSpanByNow("2019-07-24 11:11:11", TimeConstants.HOUR) + "");
                                    Log.e("TAG", "getFitTimeSpanByNow-->" + TimeUtils.getFitTimeSpanByNow(getApplicationContext(), "2019-07-24 11:11:11", 2) + "");
                                    Log.e("TAG", "getChineseZodiac-->" + TimeUtils.getChineseZodiac(getApplicationContext(), "2019-07-24 11:11:11") + "");
                                    Log.e("TAG", "getZodiac-->" + TimeUtils.getZodiac(getApplicationContext(), "2019-07-24 11:11:11") + "");
                                    Log.e("TAG", "getValueByCalendarField-->" + TimeUtils.getValueByCalendarField("2019-07-24 11:11:11", Calendar.HOUR) + "");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Log.e("TAG", "getNowMills-->" + TimeUtils.getNowMills() + "");
                                Log.e("TAG", "getNowString-->" + TimeUtils.getNowString() + "");
                                Log.e("TAG", "getChineseWeek-->" + TimeUtils.getChineseWeek(System.currentTimeMillis()) + "");
                                Log.e("TAG", "getUSWeek-->" + TimeUtils.getUSWeek(System.currentTimeMillis()) + "");
                                Log.e("TAG", "isLeapYear-->" + TimeUtils.isLeapYear(System.currentTimeMillis()) + "");
                                Log.e("TAG", "isToday-->" + TimeUtils.isToday(System.currentTimeMillis()) + "");
                                Log.e("TAG", "getStringByNow-->" + TimeUtils.getStringByNow(5, TimeConstants.HOUR) + "");
                                Log.e("TAG", "getFriendlyTimeSpanByNow-->" + TimeUtils.getFriendlyTimeSpanByNow(getApplicationContext(), System.currentTimeMillis() - TimeConstants.MSEC) + "");
                                Log.e("TAG", "getFriendlyTimeSpanByNow-->" + TimeUtils.getFriendlyTimeSpanByNow(getApplicationContext(), System.currentTimeMillis() - TimeConstants.SEC) + "");
                                Log.e("TAG", "getFriendlyTimeSpanByNow-->" + TimeUtils.getFriendlyTimeSpanByNow(getApplicationContext(), System.currentTimeMillis() - TimeConstants.MIN) + "");
                                Log.e("TAG", "getFriendlyTimeSpanByNow-->" + TimeUtils.getFriendlyTimeSpanByNow(getApplicationContext(), System.currentTimeMillis() - TimeConstants.HOUR) + "");
                                Log.e("TAG", "getFriendlyTimeSpanByNow-->" + TimeUtils.getFriendlyTimeSpanByNow(getApplicationContext(), System.currentTimeMillis() - TimeConstants.DAY) + "");
                            }
                        });
                        break;
                }
            }
        });
    }
}
