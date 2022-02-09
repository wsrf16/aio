package com.aio.portable.swiss.sugar.type;

import java.util.Random;

public abstract class IntegerSugar {
    public static final int random(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }
}
