package com.aio.portable.swiss.suite.document.base;

import java.math.BigDecimal;

/**
 * Created by York on 2017/11/27.
 */
public interface Document {
    String getValOf(String key);
    String getString(String key);
    String getString(String key, String defaultVal);
    int getInt(String key);
    int getInt(String key, int defaultVal);
    long getLong(String key);
    long getLong(String key, long defaultVal);
    BigDecimal getDecimal(String key);
    BigDecimal getDecimal(String key, BigDecimal defaultVal);
//    Date getDateTime(String key);
//    Date getDateTime(String key, Date defaultVal);
    Boolean getBool(String key);
    Boolean getBool(String key, Boolean defaultVal);
}
