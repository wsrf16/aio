package com.york.portable.swiss.assist.log.base;

import com.york.portable.swiss.assist.log.base.action.ILoggerAction;

/**
 * Created by York on 2017/11/22.
 */
public abstract class ILogger implements ILoggerAction {
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
