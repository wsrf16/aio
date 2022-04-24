package com.aio.portable.swiss.sugar.naming;

import com.aio.portable.swiss.sugar.naming.classic.Camel;
import com.aio.portable.swiss.sugar.naming.classic.Kebab;
import com.aio.portable.swiss.sugar.naming.classic.Pascal;
import com.aio.portable.swiss.sugar.naming.classic.Snake;

public class NamingStrategySugar {
    private static final Camel CAMEL = new Camel();
    private static final Pascal PASCAL = new Pascal();
    private static final Snake SNAKE = new Snake();
    private static final Kebab KEBAB = new Kebab();

    public static String camel(String name) {
        return CAMEL.format(name);
    }

    public static String pascal(String name) {
        return PASCAL.format(name);
    }

    public static String snake(String name) {
        return SNAKE.format(name);
    }

    public static String kebab(String name) {
        return KEBAB.format(name);
    }

}
