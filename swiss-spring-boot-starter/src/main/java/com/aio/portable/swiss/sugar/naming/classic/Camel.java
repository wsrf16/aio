package com.aio.portable.swiss.sugar.naming.classic;

import com.aio.portable.swiss.sugar.naming.NamingStrategyConverter;
import com.aio.portable.swiss.sugar.type.StringSugar;

public class Camel extends NamingStrategyConverter {
    @Override
    public boolean toLowerCase() {
        return false;
    }

    @Override
    public String convert(char c) {
        return String.valueOf(Character.toUpperCase(c));
    }

    @Override
    public String format(String name) {
        return StringSugar.changeFirstCharacterCase(super.format(name), false);
    }
}