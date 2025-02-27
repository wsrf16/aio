package com.aio.portable.swiss.sugar.meta;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

class StringJavaFileObject extends SimpleJavaFileObject {
    private final String code;
    public StringJavaFileObject(String className, String code) {
        super(URI.create(className), Kind.SOURCE);
        this.code = code;
    }
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors){
        return code;
    }
}
