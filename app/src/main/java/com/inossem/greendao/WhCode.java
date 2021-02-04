package com.inossem.greendao;

import com.inossem.greendao.convent.MatConvert;
import com.inossem.other.greendao.MatBean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class WhCode {
    @Id(autoincrement = true)
    private Long id = null;
    private String matCode;
    private String matName;
    private String jsonString;
    @Convert(converter = MatConvert.class, columnType = String.class)
    private MatBean mMatBean;
    @Generated(hash = 668008238)
    public WhCode(Long id, String matCode, String matName, String jsonString,
            MatBean mMatBean) {
        this.id = id;
        this.matCode = matCode;
        this.matName = matName;
        this.jsonString = jsonString;
        this.mMatBean = mMatBean;
    }
    @Generated(hash = 1047737441)
    public WhCode() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMatCode() {
        return this.matCode;
    }
    public void setMatCode(String matCode) {
        this.matCode = matCode;
    }
    public String getMatName() {
        return this.matName;
    }
    public void setMatName(String matName) {
        this.matName = matName;
    }
    public String getJsonString() {
        return this.jsonString;
    }
    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
    public MatBean getMMatBean() {
        return this.mMatBean;
    }
    public void setMMatBean(MatBean mMatBean) {
        this.mMatBean = mMatBean;
    }
}