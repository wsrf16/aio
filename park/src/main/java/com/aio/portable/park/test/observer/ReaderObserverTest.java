package com.aio.portable.park.test.observer;

import com.aio.portable.park.test.observer.MyObservable;
import com.aio.portable.park.test.observer.ReaderObserver;

public class ReaderObserverTest {
    public static final void foo() {
        ReaderObserver readerObserver = new ReaderObserver();
        MyObservable observable = new MyObservable();
        observable.addObserver(readerObserver);
        observable.publish("1111111111");
    }
}
