package com.inossem.greendao.convent;


import com.google.gson.Gson;
import com.inossem.other.greendao.MatBean;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * @author 郭晓龙
 * @time on 2020/4/2
 * @email xiaolong.guo@inossem.com
 * @describe
 */
public class MatConvert implements PropertyConverter<MatBean, String> {

    @Override
    public MatBean convertToEntityProperty(String databaseValue) {
        return new Gson().fromJson(databaseValue, MatBean.class);
    }

    @Override
    public String convertToDatabaseValue(MatBean entityProperty) {
        return new Gson().toJson(entityProperty);
    }
}
