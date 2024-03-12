package com.aio.portable.swiss.sugar.naming;

import com.aio.portable.swiss.sugar.type.StringSugar;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.Objects;

public abstract class NamingStrategyConverter {
    protected static final char UNDER_LINE = '_';
    protected static final char DASH = '-';
    protected static final char DOT = '.';

    public abstract boolean toLowerCase();

    public String format(String name) {
        if (StringUtils.isEmpty(name))
            return name;

        name = name.replace(DOT, UNDER_LINE);
        StringBuilder sb = new StringBuilder();
        boolean onlyCase = !StringSugar.containChar(name, DASH) && !StringSugar.containChar(name, UNDER_LINE);
        for (int i = 0; i < name.length(); ) {
            char current = name.charAt(i);

            if (Character.isUpperCase(current) && onlyCase) {
                sb.append(convert(current));
                i++;
            } else if ((Objects.equals(current, UNDER_LINE) || (Objects.equals(current, DASH)))
                    && (i + 1) < name.length() && Character.isLetter(name.charAt(i + 1))) {
                char after = name.charAt(i + 1);
                sb.append(convert(after));
                i += 2;
            } else {
                sb.append(current);
                i++;
            }
        }

        return toLowerCase() ? sb.toString().toLowerCase(Locale.ROOT) : sb.toString();
    }

    public abstract String convert(char c);
}