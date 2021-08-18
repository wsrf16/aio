package com.aio.portable.swiss.global;

import com.aio.portable.swiss.sugar.CollectionSugar;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ColorEnum {
    ESC_START("\u001b["), // or "\33["
    ESC_END("m"),
    BOLD("1"),
    ITALIC("3"),
    UNDERLINE("4"),
    REVERSE("7"),
    STRIKETHROUGH("9"),
    FG_BLACK("30"),
    FG_RED("31"),
    FG_GREEN("32"),
    FG_YELLOW("33"),
    FG_BLUE("34"),
    FG_MAGENTA("35"),
    FG_CYAN("36"),
    FG_WHITE("37"),
    FG_DEFAULT("39"),
    BG_BLACK("40"),
    BG_RED("41"),
    BG_GREEN("42"),
    BG_YELLOW("43"),
    BG_BLUE("44"),
    BG_PURPLE("45"),
    BG_CYAN("46"),
    BG_WHITE("47"),
    ;

    private final String code;

    public String getCode() {
        return code;
    }

    ColorEnum(String code) {
        this.code = code;
    }

    public final static String begin(ColorEnum... colors) {
        List<String> collect = Arrays.stream(colors).map(c -> c.getCode()).collect(Collectors.toList());
        return MessageFormat.format("{0}{1}{2}", ESC_START.getCode(), CollectionSugar.join(collect, ";"), ESC_END.getCode());
    }

    public final static String end() {
        return MessageFormat.format("{0}{1}", ESC_START.getCode(), ESC_END.getCode());
    }
}
