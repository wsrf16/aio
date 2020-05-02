package com.aio.portable.swiss.global;

public class Global {
    public final static void unsupportedOperationException() {
        UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
        unsupportedOperationException.printStackTrace();
//        throw unsupportedOperationException;
    }

    public final static void unsupportedOperationException(String message) {
        UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException(message);
        unsupportedOperationException.printStackTrace();
//        throw unsupportedOperationException;
    }
}
