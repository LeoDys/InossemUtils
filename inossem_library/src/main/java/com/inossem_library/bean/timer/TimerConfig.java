package com.inossem_library.bean.timer;

import com.inossem_library.R;
import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.exception.InossemException;
import com.jzxiang.pickerview.data.Type;

/**
 * 时间选择配置类
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/8/2 14:12
 * @version 1.0.7
 * @since 1.0.7
 */

public class TimerConfig {
    // 取消按钮文字
    private Integer cancelStringId;
    //  确定按钮文字
    private Integer sureStringId;
    // dailog标题
    private Integer titleStringId;
    // 年文字
    private Integer yearText;
    // 月文字
    private Integer monthText;
    // 日文字
    private Integer dayText;
    // 时文字
    private Integer hourText;
    // 分文字
    private Integer minuteText;
    // 是否循环滚动
    private Boolean cyclic;
    // 起始选择时间
    private Long minMillseconds;
    // 终止选择时间
    private Long maxMillseconds;
    // 当前默认停留时间
    private Long currentMillseconds;
    // 当前默认停留时间
    private Integer themeColor;
    /**
     * 选择类型    年月日  时分
     * <p>
     * Type.YEAR_MONTH_DAY
     * Type.ALL
     * Type.HOURS_MINS
     * Type.YEAR
     * Type.YEAR_MONTH
     * Type.MONTH_DAY_HOUR_MIN
     */
    private Type type;
    // 正常滚动的时候的日期文字的颜色
    private Integer wheelItemTextNormalColor;
    // 选中的时候的日期文字的颜色
    private Integer wheelItemTextSelectorColor;
    // 日期文字大小
    private Integer wheelItemTextSize;

    /**
     * 空构造函数 初始化所有参数的默认值
     */
    public TimerConfig() {
        initDefaultConfig();
    }

    /**
     * 初始化所有参数的默认值
     *
     * @author Leo E-mail:changwen.sun@inossem.com 2019/8/2 14:25
     * @version 1.0.7
     * @since 1.0.7
     */
    private void initDefaultConfig() {
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        cancelStringId = R.string.common_cancel;
        sureStringId = R.string.common_sure;
        titleStringId = R.string.time_picker_select;
        yearText = R.string.time_picker_select_year;
        monthText = R.string.time_picker_select_month;
        dayText = R.string.time_picker_select_day;
        hourText = R.string.time_picker_select_hour;
        minuteText = R.string.time_picker_select_minute;
        cyclic = false;
        minMillseconds = System.currentTimeMillis() - tenYears;
        maxMillseconds = System.currentTimeMillis() + tenYears;
        currentMillseconds = System.currentTimeMillis();
        themeColor = R.color.base_blue;
        type = Type.YEAR_MONTH_DAY;
        wheelItemTextNormalColor = R.color.base_gray;
        wheelItemTextSelectorColor = R.color.base_blue;
        wheelItemTextSize = 14;
    }

    public Integer getCancelStringId() {
        return cancelStringId;
    }

    public Integer getSureStringId() {
        return sureStringId;
    }

    public Integer getYearText() {
        return yearText;
    }

    public Integer getMonthText() {
        return monthText;
    }

    public Integer getDayText() {
        return dayText;
    }

    public Integer getHourText() {
        return hourText;
    }

    public Integer getMinuteText() {
        return minuteText;
    }

    public Boolean getCyclic() {
        return cyclic;
    }

    public Long getMinMillseconds() {
        return minMillseconds;
    }

    public Long getMaxMillseconds() {
        return maxMillseconds;
    }

    public Long getCurrentMillseconds() {
        return currentMillseconds;
    }

    public Integer getThemeColor() {
        return themeColor;
    }

    public Type getType() {
        return type;
    }

    public Integer getWheelItemTextNormalColor() {
        return wheelItemTextNormalColor;
    }

    public Integer getWheelItemTextSelectorColor() {
        return wheelItemTextSelectorColor;
    }

    public Integer getWheelItemTextSize() {
        return wheelItemTextSize;
    }

    public Integer getTitleStringId() {
        return titleStringId;
    }

    public TimerConfig setCancelStringId(Integer cancelStringId) {
        if (cancelStringId == null || cancelStringId == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "cancelStringId can be null or 0");
        }
        this.cancelStringId = cancelStringId;
        return this;
    }

    public TimerConfig setSureStringId(Integer sureStringId) {
        if (sureStringId == null || sureStringId == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "sureStringId can be null or 0");
        }
        this.sureStringId = sureStringId;
        return this;
    }

    public TimerConfig setYearText(Integer yearText) {
        if (yearText == null || yearText == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "yearText can be null or 0");
        }
        this.yearText = yearText;
        return this;
    }

    public TimerConfig setMonthText(Integer monthText) {
        if (monthText == null || monthText == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "monthText can be null or 0");
        }
        this.monthText = monthText;
        return this;
    }

    public TimerConfig setDayText(Integer dayText) {
        if (dayText == null || dayText == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "dayText can be null or 0");
        }
        this.dayText = dayText;
        return this;
    }

    public TimerConfig setHourText(Integer hourText) {
        if (hourText == null || hourText == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "hourText can be null or 0");
        }
        this.hourText = hourText;
        return this;
    }

    public TimerConfig setMinuteText(Integer minuteText) {
        if (minuteText == null || minuteText == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "minuteText can be null or 0");
        }
        this.minuteText = minuteText;
        return this;
    }

    public TimerConfig setCyclic(Boolean cyclic) {
        if (minMillseconds == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "cyclic can be null");
        }
        this.cyclic = cyclic;
        return this;
    }

    public TimerConfig setMinMillseconds(Long minMillseconds) {
        if (minMillseconds == null || minMillseconds == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "minMillseconds can be null or 0");
        }
        this.minMillseconds = minMillseconds;
        return this;
    }

    public TimerConfig setMaxMillseconds(Long maxMillseconds) {
        if (maxMillseconds == null || maxMillseconds == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "maxMillseconds can be null or 0");
        }
        this.maxMillseconds = maxMillseconds;
        return this;
    }

    public TimerConfig setCurrentMillseconds(Long currentMillseconds) {
        if (currentMillseconds == null || currentMillseconds == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "currentMillseconds can be null or 0");
        }
        this.currentMillseconds = currentMillseconds;
        return this;
    }

    public TimerConfig setThemeColor(Integer themeColor) {
        if (themeColor == null || themeColor == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "themeColor can be null or 0");
        }
        this.themeColor = themeColor;
        return this;
    }

    /**
     * @param type 选择的项目数
     * @return 当前类
     * @see Type.YEAR_MONTH_DAY
     * @see Type.ALL
     * @see Type.HOURS_MINS
     * @see Type.YEAR
     * @see Type.YEAR_MONTH
     * @see Type.MONTH_DAY_HOUR_MIN
     */
    public TimerConfig setType(Type type) {
        if (type == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "type can be null");
        }
        this.type = type;
        return this;
    }

    public TimerConfig setWheelItemTextNormalColor(Integer wheelItemTextNormalColor) {
        if (wheelItemTextNormalColor == null || wheelItemTextNormalColor == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "wheelItemTextNormalColor can be null or 0");
        }
        this.wheelItemTextNormalColor = wheelItemTextNormalColor;
        return this;
    }

    public TimerConfig setWheelItemTextSelectorColor(Integer wheelItemTextSelectorColor) {

        if (wheelItemTextSelectorColor == null || wheelItemTextSelectorColor == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "wheelItemTextSelectorColor can be null or 0");
        }

        this.wheelItemTextSelectorColor = wheelItemTextSelectorColor;
        return this;
    }

    public TimerConfig setWheelItemTextSize(Integer wheelItemTextSize) {
        if (wheelItemTextSize == null || wheelItemTextSize == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "wheelItemTextSize can be null or 0");
        }
        this.wheelItemTextSize = wheelItemTextSize;
        return this;
    }

    /**
     * 设置标题
     *
     * @param titleStringId 名字的string id
     * @return 当前类
     */
    public TimerConfig setTitleStringId(Integer titleStringId) {
        if (titleStringId == null || titleStringId == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "titleStringId can be null or 0");
        }
        this.titleStringId = titleStringId;
        return this;
    }
}
