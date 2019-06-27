package com.york.portable.swiss.sugar;

import java.beans.Introspector;

public class SpringUtils {
    public static String getBeanName(String shortClassName) {
        return Introspector.decapitalize(shortClassName);
    }
}
