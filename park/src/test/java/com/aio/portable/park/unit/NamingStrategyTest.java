package com.aio.portable.park.unit;

import com.aio.portable.swiss.sugar.naming.NamingStrategySugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class NamingStrategyTest {
    @Test
    public void foobar() {
        String a1 = NamingStrategySugar.snake("aA_b-cdefGhiJKLmn");
        String a3 = NamingStrategySugar.kebab("aA_b-cdefGhiJKLmn");
        String a2 = NamingStrategySugar.camel("aA_b-cdefGhiJKLmn");
        String a4 = NamingStrategySugar.pascal("aA_b-cdefGhiJKLmn");

        String a5 = NamingStrategySugar.snake("SnakeBookFuck");
        String a6 = NamingStrategySugar.kebab("SnakeBookFuck");
        String a7 = NamingStrategySugar.camel("SnakeBookFuck");
        String a8 = NamingStrategySugar.pascal("SnakeBookFuck");

        String a9 = NamingStrategySugar.snake("snake_book_fuck");
        String a10 = NamingStrategySugar.kebab("snake_book_fuck");
        String a11 = NamingStrategySugar.camel("snake_book_fuck");
        String a12 = NamingStrategySugar.pascal("snake_book_fuck");

        String a13 = NamingStrategySugar.snake("SNAKE_BOOK_FUCK");
        String a14 = NamingStrategySugar.kebab("SNAKE_BOOK_FUCK");
        String a15 = NamingStrategySugar.camel("SNAKE_BOOK_FUCK");
        String a16 = NamingStrategySugar.pascal("SNAKE_BOOK_FUCK");

        String a17 = NamingStrategySugar.snake("SNAKE");
        String a18 = NamingStrategySugar.kebab("SNAKE");
        String a19 = NamingStrategySugar.camel("SNAKE");
        String a20 = NamingStrategySugar.pascal("SNAKE");

        String a21 = NamingStrategySugar.snake("snake");
        String a22 = NamingStrategySugar.kebab("snake");
        String a23 = NamingStrategySugar.camel("snake");
        String a24 = NamingStrategySugar.pascal("snake");
        System.out.println();
    }
}
