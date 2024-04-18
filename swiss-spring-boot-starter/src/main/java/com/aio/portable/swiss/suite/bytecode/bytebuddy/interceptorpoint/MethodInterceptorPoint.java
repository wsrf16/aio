package com.aio.portable.swiss.suite.bytecode.bytebuddy.interceptorpoint;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.matcher.ElementMatcher;

public class MethodInterceptorPoint {
    public MethodInterceptorPoint(ElementMatcher<? super MethodDescription> methodMatcher, Implementation implementation) {
        this.methodMatcher = methodMatcher;
        this.implementation = implementation;
    }

    private ElementMatcher<? super MethodDescription> methodMatcher;
    private Implementation implementation;

    public ElementMatcher<? super MethodDescription> getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(ElementMatcher<? super MethodDescription> methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    public Implementation getImplementation() {
        return implementation;
    }

    public void setImplementation(Implementation implementation) {
        this.implementation = implementation;
    }
}