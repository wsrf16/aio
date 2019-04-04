package com.york.portable.swiss.sugar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TextUtils {
//	public static String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/androidT/";

    public static boolean isFirstChineseAndChar(String str) {
        String temp = str.substring(0, 0 + 1);
        String chinese = "[\u0391-\uFFE5]";
        boolean b = false;
        if (temp.matches(chinese))
            b = true;
        else
            b = false;
        return b;
    }

    public static boolean isFirstChinese(String str) {
        String temp = str.substring(0, 0 + 1);
        String chineseAndChar = "[\u4e00-\u9fa5]";
        boolean b = false;
        if (temp.matches(chineseAndChar))
            b = true;
        else
            b = false;
        return b;
    }

    public static int countByte(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
            String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
                valueLength += 2;
            } else {
				/* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    public static int countChar(String value) {
        int valueLength = 0;
//		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
            String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
//			if (temp.matches(chinese)) {
//				/* 中文字符长度为2 */
//				valueLength += 2;
//			} else
            {
				/* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }


    public static LinkedList<String> cutStringByChar(String value, int wide) {
        LinkedList<String> list = new LinkedList<String>();
        StringBuffer sb = new StringBuffer();
        int cursor_whole = 0;
        for (int i_half = 0; i_half < countChar(value);) {
            String temp = value.substring(cursor_whole, cursor_whole + 1);
            if (countChar(sb.toString()) < wide) {
//				if (isFirstChinese(temp)) {
//					sb.append(temp);
//					i_half += 2;
//				}
//				else
                {
                    sb.append(temp);
                    i_half++;
                }
                cursor_whole++;
            }
            else {
                list.add(sb.toString());
                sb = new StringBuffer();
            }
        }
        if (sb.toString().length() > 0)
            list.add(sb.toString());
        return list;
    }

    public static String pad2RightByByte(String source, char fill, int l) {
        while (true) {
            if (countByte(source) < l)
                source = fill + source;
            else
                break;
        }
        return source;
    }

    public static String pad2LeftByByte(String source, char fill, int l) {
        while (true) {
            if (countByte(source) < l)
                source = source + fill;
            else
                break;
        }
        return source;
    }

    public static String pad2BorderByByte(String source, char fill, int l, int gravity) {
        if (gravity == 0)
            return pad2LeftByByte(source, fill, l);
        else
            return pad2RightByByte(source, fill, l);
    }

    public static String pad2RightByChar(String source, char fill, int l) {
        while (true) {
            if (countChar(source) < l)
                source = fill + source;
            else
                break;
        }
        return source;
    }

    public static String pad2LeftByChar(String source, char fill, int l) {
        while (true) {
            if (countChar(source) < l)
                source = source + fill;
            else
                break;
        }
        return source;
    }

    public static String pad2BorderByChar(String source, char fill, int l, int gravity) {
        if (gravity == 0)
            return pad2LeftByChar(source, fill, l);
        else
            return pad2RightByChar(source, fill, l);
    }




    /**
     * 字符串的右对齐输出
     *
     * @param c
     *            填充字符
     * @param l
     *            填充后字符串的总长度
     * @param string
     *            要格式化的字符串
     */
    public static String flushRight(String c, long l, String string) {
        String str = "";
        String temp = "";
        if (countByte(string) > l)
            str = string;
        else
            for (int i = 0; i < l - countByte(string); i++)
                temp = temp + c;
        str = temp + string;
        return str;
    }

    /**
     * 字符串的左对齐输出
     *
     * @param c
     *            填充字符
     * @param l
     *            填充后字符串的总长度
     * @param string
     *            要格式化的字符串
     */
    public static String flushLeft(char c, long l, String string) {
        String str = "";
        String temp = "";
        if (countByte(string) > l)
            str = string;
        else
            for (int i = 0; i < l - countByte(string); i++)
                temp = temp + c;
        str = string + temp;
        return str;
    }

    public static String flushLeft(String c, long l, String string) {
        String str = "";
        String temp = "";
        if (countByte(string) > l)
            str = string;
        else
            for (int i = 0; i < l - countByte(string); i++)
                temp = temp + c;
        str = string + temp;
        return str;
    }

    public static String cutBit(double doubleVal) {
        BigDecimal b = new BigDecimal(doubleVal);
        double ret = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(ret);
    }

    public static String cutBit(double doubleVal, int bit) {
        BigDecimal b = new BigDecimal(doubleVal);
        double ret = b.setScale(bit, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(ret);
    }

    public static String carveNowDateTime(String format){
        return new SimpleDateFormat(format).format(new java.util.Date());
    }

    public static String carveYesterdayNowDateTime(String format){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        return new SimpleDateFormat(format).format(c.getTime());
    }

    public static Date string2Date(String date, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }

    /**
     * 字符串转换为java.util.Date<br>
     * 支持格式为 yyyy.MM.dd G 'at' hh:mm:ss z 如 '2002-1-1 AD at 22:10:59 PSD'<br>
     * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00'<br>
     * yy/MM/dd HH:mm:ss pm 如 '2002/1/1 17:55:00 pm'<br>
     * yy-MM-dd HH:mm:ss 如 '2002-1-1 17:55:00' <br>
     * yy-MM-dd HH:mm:ss am 如 '2002-1-1 17:55:00 am' <br>
     * @param time String 字符串<br>
     * @return Date 日期<br>
     */
    public static Date string2Date(String time){
        SimpleDateFormat formatter;
        int tempPos=time.indexOf("AD") ;
        time=time.trim() ;
        formatter = new SimpleDateFormat ("yyyy.MM.dd G 'at' hh:mm:ss z");
        if(tempPos>-1){
            time=time.substring(0,tempPos)+
                    "公元"+time.substring(tempPos+"AD".length());//china
            formatter = new SimpleDateFormat ("yyyy.MM.dd G 'at' hh:mm:ss z");
        }
        tempPos=time.indexOf("-");
        if(tempPos>-1&&(time.indexOf(" ")<0)){
            formatter = new SimpleDateFormat ("yyyyMMddHHmmssZ");
        }
        else if((time.indexOf("/")>-1) &&(time.indexOf(" ")>-1)){
            formatter = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss");
        }
        else if((time.indexOf("-")>-1) &&(time.indexOf(" ")>-1)){
            formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        }
        else if((time.indexOf("/")>-1) &&(time.indexOf("am")>-1) ||(time.indexOf("pm")>-1)){
            formatter = new SimpleDateFormat ("yyyy-MM-dd KK:mm:ss a");
        }
        else if((time.indexOf("-")>-1) &&(time.indexOf("am")>-1) ||(time.indexOf("pm")>-1)){
            formatter = new SimpleDateFormat ("yyyy-MM-dd KK:mm:ss a");
        }
        ParsePosition pos = new ParsePosition(0);
        java.util.Date ctime = formatter.parse(time, pos);

        return ctime;
    }

    public static int pickMax(Integer[] ints) {
        int curMax = ints[0];
        for (int i : ints) {
            if (i > curMax)
                curMax = i;
        }
        return curMax;
    }

    public static int pickMin(int[] ints) {
        int curMin = ints[0];
        for(int i : ints) {
            if (i < curMin)
                curMin = i;
        }
        return curMin;
    }

    public static boolean ArrayContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    public static String generateRandomNum(int n) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String removeStringFrom(String str, int begin, int end) {
        return str.substring(0, begin) + str.substring(end, str.length());
    }






    public static String gzipToString(byte[] bytes, String encoding) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString(encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static byte[] ungzip(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            gzip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}
