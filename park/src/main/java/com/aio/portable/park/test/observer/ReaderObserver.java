package com.aio.portable.park.test.observer;

import java.util.Observable;
import java.util.Observer;

public class ReaderObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(o);
        System.out.println(arg);
    }
}
