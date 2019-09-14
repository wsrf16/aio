package com.aio.portable.swiss.sugar;

import org.springframework.util.StringUtils;

import java.util.Locale;

public class NamingStrategy {
    public static class Snake {
        public final static boolean isCaseInsensitive = true;

        public final static String snake(String name) {
            if (!StringUtils.hasText(name))
                return name;

            StringBuilder builder = new StringBuilder(name.replace('.', '_'));

            for(int i = 1; i < builder.length() - 1; ++i) {
                if (isUnderscoreRequired(builder.charAt(i - 1), builder.charAt(i), builder.charAt(i + 1))) {
                    builder.insert(i++, '_');
                }
            }

            if (isCaseInsensitive) {
                name = builder.toString().toLowerCase(Locale.ROOT);
            }

            return name;
        }

        private final static boolean isUnderscoreRequired(char before, char current, char after) {
            return Character.isLowerCase(before) && Character.isUpperCase(current) && Character.isLowerCase(after);
        }
    }

    public static class Camel {
        public final static boolean isCaseInsensitive = false;
        public final static char UNDER_LINE = '_';

        public final static String camel(String name) {
            if (!StringUtils.hasText(name))
                return name;

            StringBuilder builder = new StringBuilder();
            for(int i = 1; i < name.length() - 1;) {
                if (isUnderscoreRequired(name.charAt(i - 1), name.charAt(i), name.charAt(i + 1))) {
                    builder.append(name.charAt(i - 1) + Character.toUpperCase(name.charAt(i + 1)));
                    i += 3;
                } else {
                    builder.append(name.charAt(i - 1));
                    i++;
                }
            }

            if (isCaseInsensitive) {
                name = builder.toString().toLowerCase(Locale.ROOT);
            }

            return name;
        }

        private final static boolean isUnderscoreRequired(char before, char current, char after) {
            return Character.isLowerCase(before) && (current == UNDER_LINE) && Character.isLowerCase(after);
        }
    }

}
