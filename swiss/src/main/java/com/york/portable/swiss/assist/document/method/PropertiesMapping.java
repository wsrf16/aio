package com.york.portable.swiss.assist.document.method;

import com.york.portable.swiss.assist.document.base.AbsDocument;
import com.york.portable.swiss.assist.document.base.AbsDocument;

import java.io.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;

public class PropertiesMapping extends AbsDocument {
    //Properties properties = new Properties();

    Properties pps = new Properties();
    public final String filePath;

    public static PropertiesMapping instance() {
        try {
            return new PropertiesMapping("log.properties");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PropertiesMapping(String filePath) throws IOException {
        this.filePath = filePath;
        if (new File(filePath).exists()) {
            InputStream in = new FileInputStream(filePath);
            pps.load(in);
        }
    }

    public String getValOf(String key) {
        try {
            return getValueByKey(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getValueByKey(String key) throws IOException {
        String value = pps.getProperty(key);
        return value;
    }

    public void setProperties(String pKey, String pValue) throws IOException {
        setProperties(filePath, pKey, pValue);
    }

    public void setProperties(String filePath, String pKey, String pValue) throws IOException {
        //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
        //强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
        OutputStream out = new FileOutputStream(filePath);
        pps.setProperty(pKey, pValue);
        //以适合使用 load 方法加载到 Properties 表中的格式，
        //将此 Properties 表中的属性列表（键和元素对）写入输出流
        pps.store(out, "Update " + pKey + " name");
    }



    private static class BlahUnit {
        private static void todo() throws IOException {
            PropertiesMapping pps = new PropertiesMapping("1.properties");
            BigDecimal v1 = pps.getDecimal("AAA");
//            Date v2 = pps.getDateTime("BBB");
            String v3 = pps.getString("CCCD", "888");
        }
    }

}
