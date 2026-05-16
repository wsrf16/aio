package com.aio.portable.swiss.global;

public class Global {
    public static void unsupportedOperationException() {
        UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
        unsupportedOperationException.printStackTrace();
//        throw unsupportedOperationException;
    }

    public static void unsupportedOperationException(String message) {
        UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException(message);
        unsupportedOperationException.printStackTrace();
//        throw unsupportedOperationException;
    }
}
