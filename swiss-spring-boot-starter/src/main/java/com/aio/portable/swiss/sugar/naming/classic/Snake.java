package com.aio.portable.swiss.sugar.naming.classic;

import com.aio.portable.swiss.sugar.naming.NamingStrategyConverter;
import com.aio.portable.swiss.sugar.type.StringSugar;

public class Snake extends NamingStrategyConverter {
    @Override
    public boolean toLowerCase() {
        return false;
    }

    @Override
    public String convert(char c) {
        return String.valueOf(UNDER_LINE) + Character.toLowerCase(c);
    }

    @Override
    public String format(String name) {
        return StringSugar.trimStart(super.format(name), String.valueOf(UNDER_LINE));
    }
}
