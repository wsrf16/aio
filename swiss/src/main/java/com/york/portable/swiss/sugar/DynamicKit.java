package com.york.portable.swiss.sugar;

//import javafx.util.Pair;

//import java.lang.reflect.Field;
//import java.util.*;
//
///**
// * Created by York on 2017/11/23.
// */
//@Deprecated
//public class DynamicKit {
//    public static Map<String, Object> object2Map(Object obj) {
//        //ImmutablePair
//        //Pair
//        return object2Map(obj, false);
//    }
//
//    public static Map<String, Object> object2Map(Object obj, Boolean nullValueHandling) {
//        Field[] propertyInfos = obj.getClass().getFields();
//        Field[] propertyInfos2Handle = Arrays.stream(propertyInfos).filter(c -> {
//            try {
//
//                return nullValueHandling ? true : c.get(obj) != null;
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//                return false;
//            }
//        }).toArray(Field[]::new);
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        Arrays.stream(propertyInfos2Handle).forEach(c -> {
//            try {
//                map.put(c.getName(), c.get(obj));
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        });
//        return map;
//    }
//
////    /// <summary>
////    /// Dictionary2Dynamic
////    /// </summary>
////    /// <param name="dict"></param>
////    /// <returns></returns>
////    public static dynamic Dictionary2Dynamic(IDictionary<String, Object> dict)
////    {
////        dynamic dynamicObj = new System.DynamicKit.ExpandoObject();
////        IDictionary<String, Object> dynamicDict = (IDictionary<String, Object>)dynamicObj;
////        foreach (var item in dict)
////        {
////            dynamicDict[item.Key] = item.Value;
////        }
////        return dynamicObj;
////    }
//
//
////    public static dynamic Object2Dynamic(Object obj, Boolean nullValueHandling = false)
////    {
////        var dict = Object2Dictionary(obj, nullValueHandling);
////        return Dictionary2Dynamic(dict);
////    }
//
////    /// <summary>
////    /// AddOrSetDynamic
////    /// </summary>
////    /// <param name="dynamicObj">dynamicObj is ExpandoObject</param>
////    /// <param name="key"></param>
////    /// <param name="val"></param>
////    public static void AddOrSetDynamic(dynamic dynamicObj, String key, Object val)
////    {
////        IDictionary<String, Object> dynamicDict = (IDictionary<String, Object>)dynamicObj;
////        dynamicDict[key] = val;
////    }
//
////    /// <summary>
////    /// AddDynamic
////    /// </summary>
////    /// <param name="dynamicObj">dynamicObj is ExpandoObject</param>
////    /// <param name="key"></param>
////    /// <param name="val"></param>
////    public static void AddDynamic(dynamic dynamicObj, String key, Object val)
////    {
////        IDictionary<String, Object> dynamicDict = (IDictionary<String, Object>)dynamicObj;
////        dynamicDict.Add(key, val);
////    }
//
////    /// <summary>
////    /// RemoveDynamic
////    /// </summary>
////    /// <param name="dynamicObj"></param>
////    /// <param name="key"></param>
////    /// <param name="val"></param>
////    public static void RemoveDynamic(dynamic dynamicObj, String key, Object val)
////    {
////        IDictionary<String, Object> dynamicDict = (IDictionary<String, Object>)dynamicObj;
////        dynamicDict.Remove(key);
////    }
//}