package com.york.portable.swiss.assist.log.base;

/**
 * Created by York on 2017/11/22.
 */
public abstract class LoggerRoot implements LoggerAction {
    public String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public abstract void dispose();
    //IList<string> PrefixList { get; set; }
    //void openSingleIPPrefix();
    //void OpenMutiIPPrefix();
    //void CloseMutiIPPrefix();
}
