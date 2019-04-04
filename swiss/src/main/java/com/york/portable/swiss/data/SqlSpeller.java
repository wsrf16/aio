//package com.york.portable.swiss.db;
//
//import com.york.portable.swiss.sugar.CommonBeanUtils;
//import com.york.portable.swiss.sugar.DynamicKit;
//import com.york.portable.swiss.sugar.CommonBeanUtils;
//import com.york.portable.swiss.sugar.DynamicKit;
//import org.apache.commons.beanutils.BeanUtils;
//
//import java.lang.reflect.InvocationTargetException;
//import java.text.MessageFormat;
//import java.util.Arrays;
//import java.util.Map;
//import java.util.Set;
//
///**
// * Created by York on 2017/11/23.
// */
//@Deprecated
//public class SqlSpeller {
//
//    /**
//     * spellInsertText
//     * @param specification
//     * @return text as "(col1,col2) VALUES ('a',2)"
//     */
//    public static String spellInsertText(Object specification) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//        Map kvPairs = DynamicKit.object2Map(specification);
////        Map kvPairs = CommonBeanUtils.object2Map(specification);
//        Set items = kvPairs.keySet();
//        String text1 = MessageFormat.format(" {0} ", String.join(",", items));
//        String text2 = MessageFormat.format(" {0} ", String.join(",", Arrays.stream(items.toArray()).map(c -> "@" + c).toArray(String[]::new)));
//        String text = MessageFormat.format("({0}) VALUES ({1})", text1, text2);
//        return text;
//    }
//
//    /**
//     * spellUpdateText
//     * @param specification
//     * @return text as "col1='a',col2=2"
//     */
//    public static String spellUpdateText(Object specification) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//        Map kvPairs = DynamicKit.object2Map(specification);
////        Map kvPairs = CommonBeanUtils.object2Map(specification, false);
//        Set items = kvPairs.entrySet();
//        String[] textItems = Arrays.stream(items.toArray()).map(c -> MessageFormat.format("{0}={1}", ((Map.Entry) c).getKey(), convert(((Map.Entry) c).getValue()))).toArray(String[]::new);
//        String text = String.join(",", textItems);
//        return text;
//    }
//
//    /**
//     * spellSelectText
//     * @param specification
//     * @return text as "col1,col2"
//     */
//    public static String spellSelectText(Object specification) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//        Map kvPairs = DynamicKit.object2Map(specification, true);
////        Map kvPairs = CommonBeanUtils.object2Map(specification, false);
//        Set items = kvPairs.keySet();
//        Set textItems = items;
//        String text = String.join(",", textItems);
//        return text;
//    }
//
//    /**
//     * spellConditionText
//     * @param specification
//     * @return col1=@col1 and col2=@col2
//     */
//    public static String spellConditionText(Object specification) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
////        Map kvPairs = DynamicKit.object2Map(specification);
//        Map kvPairs = CommonBeanUtils.object2Map(specification, false);
//        Set items = kvPairs.keySet();
//        String[] textItems = Arrays.stream(items.toArray()).map(c -> MessageFormat.format("{0}=@{1}",c, c)).toArray(String[]::new);
//        String text = String.join(" and ", textItems);
//        return text;
//    }
//
//    protected static String convert(Object obj) {
//        //switch (obj)
//        //{
//        //    case isNumberType(obj):
//        //        break;
//        //}
//        if (isNumberType(obj))
//            return obj.toString();
//        else if (isNull(obj))
//            return obj.toString();
//        else
//            return String.format("'{0}'", obj);
//    }
//
//    protected static Boolean isNumberType(Object obj) {
//        Boolean b = false;
//        b |= obj instanceof Short;
//        b |= obj instanceof Integer;
//        b |= obj instanceof Long;
//        return b;
//    }
//
//    protected static Boolean isNull(Object obj) {
//        Boolean b = false;
//        b |= obj == null;
//        return b;
//    }
//}