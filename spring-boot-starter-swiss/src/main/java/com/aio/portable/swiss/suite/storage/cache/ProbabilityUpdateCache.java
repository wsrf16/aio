package com.aio.portable.swiss.suite.storage.cache;

import java.util.function.Supplier;

public class ProbabilityUpdateCache<T> {
    private T t;
    private Supplier<T> refresh;
    private int intervalSeconds = 5 * 60;
    private Thread intervalUpdate = new Thread() {
        @Override
        public void run() {
            super.run();
            while (isContinue) {
                try {
                    update();
                    Thread.sleep(intervalSeconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private boolean isContinue = true;

    public ProbabilityUpdateCache(Supplier<T> refresh) {
        this.refresh = refresh;
    }

    public void update() {
        T t = refresh.get();
        this.t = t;
    }

    public T get() {
        return this.t;
    }
}
