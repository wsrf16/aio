package com.aio.portable.swiss.sugar;

import org.springframework.util.StringUtils;

import java.util.Locale;

public class NamingStrategySugar {
    public static String camel(String name) {
        return Camel.format(name);
    }

    public static String pascal(String name) {
        return Pascal.format(name);
    }

    public static String snake(String name) {
        return Snake.format(name);
    }

    public static String kebab(String name) {
        return Kebab.format(name);
    }

    private static class Camel {
        public static boolean isCaseInsensitive = false;
        public static final char UNDER_LINE = '_';
        public static final char DASH = '-';

        public static final String format(String name) {
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

        private static final boolean isUnderscoreRequired(char before, char current, char after) {
            return Character.isLowerCase(before) &&
                    ((current == UNDER_LINE) && (current == DASH)) &&
                    Character.isLowerCase(after);
        }
    }

    private static class Pascal {
        public static boolean isCaseInsensitive = false;
        public static final char UNDER_LINE = '_';
        public static final char DASH = '-';

        public static final String format(String name) {
            name = Camel.format(name);
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
    }

    private static class Snake {
        public static boolean isCaseInsensitive = true;
        public static final char UNDER_LINE = '_';
        public static final char DASH = '-';

        public static final String format(String name) {
            if (!StringUtils.hasText(name))
                return name;

            StringBuilder builder = new StringBuilder(name.replace('.', UNDER_LINE));

            for(int i = 1; i < builder.length() - 1; ++i) {
                if (isUnderscoreRequired(builder.charAt(i - 1), builder.charAt(i), builder.charAt(i + 1))) {
                    builder.insert(i++, UNDER_LINE);
                }
            }

            if (isCaseInsensitive) {
                name = builder.toString().toLowerCase(Locale.ROOT);
            }

            return name;
        }

        private static final boolean isUnderscoreRequired(char before, char current, char after) {
            return Character.isLowerCase(before) &&
                    (Character.isUpperCase(current) && current == DASH) &&
                    Character.isLowerCase(after);
        }
    }

    private static class Kebab {
        public static boolean isCaseInsensitive = true;
        public static final char UNDER_LINE = '_';
        public static final char DASH = '-';

        public static final String format(String name) {
            if (!StringUtils.hasText(name))
                return name;

            StringBuilder builder = new StringBuilder(name.replace('.', DASH));

            for(int i = 1; i < builder.length() - 1; ++i) {
                if (isDashRequired(builder.charAt(i - 1), builder.charAt(i), builder.charAt(i + 1))) {
                    builder.insert(i++, DASH);
                }
            }

            if (isCaseInsensitive) {
                name = builder.toString().toLowerCase(Locale.ROOT);
            }

            return name;
        }

        private static final boolean isDashRequired(char before, char current, char after) {
            return Character.isLowerCase(before) &&
                    Character.isUpperCase(current) &&
                    Character.isLowerCase(after);
        }
    }

}
