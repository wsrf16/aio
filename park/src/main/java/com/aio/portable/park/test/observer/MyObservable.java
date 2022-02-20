package com.aio.portable.park.test.observer;

import java.util.Observable;

public class MyObservable extends Observable {

    /**
     * 发表文章
     * @param article
     */
    public void publish(String article){
        // 发表文章

        // 改变状态
        this.setChanged();

        // 通知所有观察者
        this.notifyObservers("arg1,arg2");
    }
}
