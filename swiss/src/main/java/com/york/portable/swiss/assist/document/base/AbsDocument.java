package com.york.portable.swiss.assist.document.base;

import com.york.portable.swiss.sugar.TextUtils;
import com.york.portable.swiss.sugar.TextUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by York on 2017/11/27.
 */
public abstract class AbsDocument implements IDocument {
    //public java.util.function.Function<String, String> getValOf;

    private String valOfKey(String key) {
        return getValOf(key);
    }


    public String getString(String key) {
        String val = valOfKey(key);
        return val;
    }

    public String getString(String key, String defaultVal) {
        String _val = valOfKey(key);
        String val = _val != null ? _val : defaultVal;
        return val;
    }

    public int getInt(String key) {
        String _val = valOfKey(key);
        int val = Integer.valueOf(_val);
        return val;
    }

    public int getInt(String key, int defaultVal) {
        String _val = valOfKey(key);
        int val = _val != null ? Integer.valueOf(_val) : defaultVal;
        return val;
    }

    public long getLong(String key) {
        String _val = valOfKey(key);
        long val = Long.valueOf(_val);
        return val;
    }

    public long getLong(String key, long defaultVal) {
        String _val = valOfKey(key);
        long val = _val != null ? Long.valueOf(_val) : defaultVal;
        return val;
    }

    public BigDecimal getDecimal(String key) {
        String _val = valOfKey(key);
        BigDecimal val = new BigDecimal(_val);
        return val;
    }

    public BigDecimal getDecimal(String key, BigDecimal defaultVal) {
        String _val = valOfKey(key);
        BigDecimal val = _val != null ? new BigDecimal(_val) : defaultVal;
        return val;
    }

    public Date getDateTime(String key) {
        String _val = valOfKey(key);
        Date val = TextUtils.string2Date(_val);
        return val;
    }

    public Date getDateTime(String key, Date defaultVal) {
        String _val = valOfKey(key);
        Date val = _val != null ? TextUtils.string2Date(_val) : defaultVal;
        return val;
    }


    public Boolean getBool(String key) {
        String _val = valOfKey(key);
        Boolean val = Boolean.parseBoolean(_val);
        return val;
    }

    public Boolean getBool(String key, Boolean defaultVal) {
        String _val = valOfKey(key);
        Boolean val = Boolean.parseBoolean(_val);
        return val;
    }
}
