package com.aio.portable.swiss.sugar.type;

import com.aio.portable.swiss.global.ColorEnum;
import com.aio.portable.swiss.sugar.RegexSugar;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.regex.Pattern;

public class StringSugar {
    public final static String EMPTY = "";

    public final static String trim(String str, String removed) {
        str = trimStart(str, removed);
        str = trimEnd(str, removed);
        return str;
    }

    public final static String trim(String str, String start, String end) {
        String result = trimStart(str, start);
        result = trimEnd(result, end);
        return result;
    }

    public final static String trimStart(String str, String removedStart) {
        if (org.springframework.util.StringUtils.hasLength(str) && org.springframework.util.StringUtils.hasLength(removedStart)) {
            return str.startsWith(removedStart) ? str.substring(removedStart.length()) : str;
        } else {
            return str;
        }
    }

    public final static String trimEnd(String str, String removedEnd) {
        if (org.springframework.util.StringUtils.hasLength(str) && org.springframework.util.StringUtils.hasLength(removedEnd)) {
            return str.endsWith(removedEnd) ? str.substring(0, str.length() - removedEnd.length()) : str;
        } else {
            return str;
        }
    }

    public final static String trimAllStart(String text, String removedStart) {
        String result = text;
        while (result.startsWith(removedStart)) {
            result = StringSugar.trimStart(result, removedStart);
        }
        return result;
    }


    public final static String trimAllEnd(String text, String removedEnd) {
        String result = text;
        while (result.endsWith(removedEnd)) {
            result = StringSugar.trimEnd(result, removedEnd);
        }
        return result;
    }

    public final static String replaceEach(final String text, final String[] searchList, final String[] replacementList) {
        return replaceEach(text, searchList, replacementList, false, 0);
    }

    private static String replaceEach(final String text, final String[] searchList, final String[] replacementList, final boolean repeat, final int timeToLive) {

        // mchyzer Performance note: This creates very few new objects (one major goal)
        // let me know if there are performance requests, we can create a harness to measure

        if (!org.springframework.util.StringUtils.hasText(text) || CollectionSugar.isEmpty(searchList) || CollectionSugar.isEmpty(replacementList)) {
            return text;
        }

        // if recursing, this shouldn't be less than 0
        if (timeToLive < 0) {
            throw new IllegalStateException("Aborting to protect against StackOverflowError - " +
                    "output of one loop is the input of another");
        }

        int searchLength = searchList.length;
        int replacementLength = replacementList.length;

        // make sure lengths are ok, these need to be equal
        if (searchLength != replacementLength) {
            throw new IllegalArgumentException("Search and Replace array lengths don't match: "
                    + searchLength
                    + " vs "
                    + replacementLength);
        }

        // keep track of which still have matches
        boolean[] noMoreMatchesForReplIndex = new boolean[searchLength];

        // index on index that the match was found
        int textIndex = -1;
        int replaceIndex = -1;
        int tempIndex = -1;

        // index of replace array that will replace the search string found
        // NOTE: logic duplicated below START
        for (int i = 0; i < searchLength; i++) {
            if (noMoreMatchesForReplIndex[i] || searchList[i] == null ||
                    searchList[i].isEmpty() || replacementList[i] == null) {
                continue;
            }
            tempIndex = text.indexOf(searchList[i]);

            // see if we need to keep searching for this
            if (tempIndex == -1) {
                noMoreMatchesForReplIndex[i] = true;
            } else {
                if (textIndex == -1 || tempIndex < textIndex) {
                    textIndex = tempIndex;
                    replaceIndex = i;
                }
            }
        }
        // NOTE: logic mostly below END

        // no search strings found, we are done
        if (textIndex == -1) {
            return text;
        }

        int start = 0;

        // get a good guess on the size of the result buffer so it doesn't have to double if it goes over a bit
        int increase = 0;

        // count the replacement text elements that are larger than their corresponding text being replaced
        for (int i = 0; i < searchList.length; i++) {
            if (searchList[i] == null || replacementList[i] == null) {
                continue;
            }
            int greater = replacementList[i].length() - searchList[i].length();
            if (greater > 0) {
                increase += 3 * greater; // assume 3 matches
            }
        }
        // have upper-bound at 20% increase, then let Java take over
        increase = Math.min(increase, text.length() / 5);

        StringBuilder buf = new StringBuilder(text.length() + increase);

        while (textIndex != -1) {

            for (int i = start; i < textIndex; i++) {
                buf.append(text.charAt(i));
            }
            buf.append(replacementList[replaceIndex]);

            start = textIndex + searchList[replaceIndex].length();

            textIndex = -1;
            replaceIndex = -1;
            tempIndex = -1;
            // find the next earliest match
            // NOTE: logic mostly duplicated above START
            for (int i = 0; i < searchLength; i++) {
                if (noMoreMatchesForReplIndex[i] || searchList[i] == null ||
                        searchList[i].isEmpty() || replacementList[i] == null) {
                    continue;
                }
                tempIndex = text.indexOf(searchList[i], start);

                // see if we need to keep searching for this
                if (tempIndex == -1) {
                    noMoreMatchesForReplIndex[i] = true;
                } else {
                    if (textIndex == -1 || tempIndex < textIndex) {
                        textIndex = tempIndex;
                        replaceIndex = i;
                    }
                }
            }
            // NOTE: logic duplicated above END

        }
        int textLength = text.length();
        for (int i = start; i < textLength; i++) {
            buf.append(text.charAt(i));
        }
        String result = buf.toString();
        if (!repeat) {
            return result;
        }

        return replaceEach(result, searchList, replacementList, repeat, timeToLive - 1);
    }


    public final static String repeat(char ch, int repeat) {
        if (repeat <= 0) {
            return "";
        } else {
            char[] buf = new char[repeat];

            for (int i = repeat - 1; i >= 0; --i) {
                buf[i] = ch;
            }

            return new String(buf);
        }
    }


    public final static String leftPad(int no, int length) {
        /*
         * 0 指前面补充零
         * formatLength 字符总长度为 formatLength
         * d 代表为正数。
         */
        String newString = String.format("%0" + length + "d", no);
        return newString;
    }

    public final static String rightPad(String str, int length, char padChar) {
        if (str == null) {
            return null;
        } else {
            int pads = length - str.length();
            if (pads <= 0) {
                return str;
            } else {
                return pads > 8192 ? rightPad(str, length, String.valueOf(padChar)) : str.concat(repeat(padChar, pads));
            }
        }
    }

    public final static String unwrap(String input, String boundary) {
        String s = trimStart(input, boundary);
        s = trimEnd(s, boundary);
        return s;
    }

    public final static String unwrapAll(String input, String boundary) {
        String s = trimAllStart(input, boundary);
        s = trimAllEnd(s, boundary);
        return s;
    }

    public final static String wrap(String input, String boundary) {
        String s = MessageFormat.format("{1}{0}{1}", input, boundary);
        return s;
    }

    private final static String rightPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        } else {
            if (!StringUtils.hasLength(padStr)) {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0) {
                return str;
            } else if (padLen == 1 && pads <= 8192) {
                return rightPad(str, size, padStr.charAt(0));
            } else if (pads == padLen) {
                return str.concat(padStr);
            } else if (pads < padLen) {
                return str.concat(padStr.substring(0, pads));
            } else {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for (int i = 0; i < pads; ++i) {
                    padding[i] = padChars[i % padLen];
                }

                return str.concat(new String(padding));
            }
        }
    }

    public final static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        } else {
            int pads = size - str.length();
            if (pads <= 0) {
                return str;
            } else {
                return pads > 8192 ? leftPad(str, size, String.valueOf(padChar)) : repeat(padChar, pads).concat(str);
            }
        }
    }

    public final static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        } else {
            if (!StringUtils.hasLength(padStr)) {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0) {
                return str;
            } else if (padLen == 1 && pads <= 8192) {
                return leftPad(str, size, padStr.charAt(0));
            } else if (pads == padLen) {
                return padStr.concat(str);
            } else if (pads < padLen) {
                return padStr.substring(0, pads).concat(str);
            } else {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for (int i = 0; i < pads; ++i) {
                    padding[i] = padChars[i % padLen];
                }

                return (new String(padding)).concat(str);
            }
        }
    }

    public final static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public final static boolean containUpperCase(String name) {
        String regex=".*[A-Z]+.*";
        return Pattern.compile(regex).matcher(name).matches();
    }

    public final static boolean containLowerCase(String name) {
        String regex=".*[a-z]+.*";
        return Pattern.compile(regex).matcher(name).matches();
    }

    private final static String PLACE_HOLDER = "\\{\\}";

    /**
     * format
     *
     * @param input
     * @param replacement
     * @return
     */
    public static String format(String input, Object... replacement) {
        return RegexSugar.replace(input, PLACE_HOLDER, replacement);
    }

    public final static String paint(String content, ColorEnum... colors) {
//        System.out.format("\33[%d;%dm%s%n", foreground, n, content);
        return MessageFormat.format("{0}{1}{2}", ColorEnum.begin(colors), content, ColorEnum.end());
    }

    public final static boolean isCapitalize(String word) {
        char first = word.charAt(0);
        boolean isUpperCase = Character.isUpperCase(first);
        return isUpperCase;
    }

    public final static String getLastWord(String text, String interval) {
        int local = text.lastIndexOf(interval) + 1;
        String part = text.substring(local);
        return part;
    }

    public final static String getFirstWord(String text, String interval) {
        int local = text.indexOf(interval);
        String part = text.substring(0, local);
        return part;
    }

    public final static String getFirstHasText(String otherwise, String... str) {
        for (String s : str) {
            if (StringUtils.hasText(s))
                return s;
        }
        return otherwise;
    }

    public final static String getFirstHasLength(String otherwise, String... str) {
        for (String s : str) {
            if (StringUtils.hasLength(s))
                return s;
        }
        return otherwise;
    }

    public final static String getFirstIsEmpty(String otherwise, String... str) {
        for (String s : str) {
            if (StringUtils.isEmpty(s))
                return s;
        }
        return otherwise;
    }



}
