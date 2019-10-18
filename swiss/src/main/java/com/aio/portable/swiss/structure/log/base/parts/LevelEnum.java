package com.aio.portable.swiss.structure.log.base.parts;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by York on 2017/11/22.
 */
public enum LevelEnum {
    VERBOSE("verbose", "Anything and everything you might want to know about a running block of code."),
    TRACE("trace", "."),
    INFO("info", "The lifeblood of operational intelligence - things happen."),
    DEBUG("debug", "Internal system events that aren't necessarily observable from the outside."),
    WARNING("warning", "Service is degraded or endangered."),
    ERROR("error", "Functionality is unavailable, invariants are broken or data is lost."),
    FATAL("fatal", "If you have a pager, it goes off when one of these occurs.");

    LevelEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private String name;
    private String description;

    @JsonValue
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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


