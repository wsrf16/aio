package com.aio.portable.swiss.suite.log.impl;

import com.aio.portable.swiss.sugar.StackTraceSugar;

public interface BuilderBy<T> {
//    default T build(Class<?> clazz) {
//        String name = clazz.toString();
//        return build(name);
//    }
//
//    default T build() {
//        String name = StackTraceSugar.Previous.getClassName();
//        return build(name);
//    }
//
////    static <T> T staticBuild(String name) {
//////        return this.build(name);
////        Class<T> clazz = null;
////        T t = ClassSugar.newInstance(clazz, String.class);
////        return t;
////    }
////
////    static <T> T staticBuild(Class<?> clazz) {
////        String name = clazz.toString();
////        return staticBuild(name);
////    }
////
////    static <T> T staticBuild() {
////        String name = StackTraceSugar.Previous.getClassName();
////        return staticBuild(name);
////    }
}
