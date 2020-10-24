package com.aio.portable.swiss.suite.log.parts;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by York on 2017/11/22.
 */
public enum LevelEnum {
    ALL("all", 00, "all"),
    VERBOSE("verbose", 10, "Anything and everything you might want to know about a running block of code."),
    TRACE("trace", 20, "."),
    DEBUG("debug", 30, "Internal system events that aren't necessarily observable from the outside."),
    INFORMATION("info", 40, "The lifeblood of operational intelligence - things happen."),
    WARNING("warn", 50, "Service is degraded or endangered."),
    ERROR("error", 60, "Functionality is unavailable, invariants are broken or data is lost."),
    FATAL("fatal", 70, "If you have a pager, it goes off when one of these occurs."),
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

    public boolean match(LevelEnum levelEnum) {
        return levelEnum.getPriority() >= priority;
    }
}


//class LevelEnum {
//    //[Description("Verbose")]
//    public final static int Verbose = 0;
//    public final static String Verbose() {return  "Verbose";}
//    //[Description("Info")]
//    public final static int Info = 1;
//    public final static String Info() {return  "Info";}
//    //[Description("Debug")]
//    public final static int Debug = 2;
//    public final static String Debug() {return  "Debug";}
//    //[Description("Warning")]
//    public final static int Warning = 3;
//    public final static String Warning() {return  "Warning";}
//    //[Description("Error")]
//    public final static int Error = 4;
//    public final static String Error() {return  "Error";}
//    //[Description("Fatal")]
//    public final static int Fatal = 5;
//    public final static String Fatal() {return  "Fatal";}
//}


