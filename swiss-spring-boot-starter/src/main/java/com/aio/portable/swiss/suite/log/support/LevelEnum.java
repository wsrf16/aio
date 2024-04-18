package com.aio.portable.swiss.suite.log.support;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by York on 2017/11/22.
 */
public enum LevelEnum {
    ALL("all", 00, "all"),
    VERB("verbose", 10, "verbose: Anything and everything you might want to know about a running block of code."),
    TRACE("trace", 20, "."),
    DEBUG("debug", 30, "debug: Internal system events that aren't necessarily observable from the outside."),
    INFO("info", 40, "information: The lifeblood of operational intelligence - things happen."),
    WARN("warn", 50, "warning: Service is degraded or endangered."),
    ERROR("error", 60, "error: Functionality is unavailable, invariants are broken or data is lost."),
    FATAL("fatal", 70, "fatal: If you have a pager, it goes off when one of these occurs."),
    OFF("off", Integer.MAX_VALUE, "off");


    // org.springframework.boot.logging.LogLevel
    // org.springframework.boot.logging.LoggingSystem
    LevelEnum(String name, Integer priority, String description) {
        this.name = name;
        this.priority = priority;
        this.description = description;
    }

    private String name;
    private Integer priority;
    private String description;

    @JsonValue
    public String getName() {
        return name;
    }

    public Integer getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public boolean beMatched(LevelEnum levelEnum) {
//        return levelEnum.getPriority() >= priority;
        return levelEnum.ordinal() >= this.ordinal();
    }
}


//class LevelEnum {
//    //[Description("Verbose")]
//    public static final int Verbose = 0;
//    public static final String Verbose() {return  "Verbose";}
//    //[Description("Info")]
//    public static final int Info = 1;
//    public static final String Info() {return  "Info";}
//    //[Description("Debug")]
//    public static final int Debug = 2;
//    public static final String Debug() {return  "Debug";}
//    //[Description("Warning")]
//    public static final int Warning = 3;
//    public static final String Warning() {return  "Warning";}
//    //[Description("Error")]
//    public static final int Error = 4;
//    public static final String Error() {return  "Error";}
//    //[Description("Fatal")]
//    public static final int Fatal = 5;
//    public static final String Fatal() {return  "Fatal";}
//}


