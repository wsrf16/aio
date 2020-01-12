package com.aio.portable.swiss.structure.bytecode.bytebuddy.interceptorpoint;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

public class TypeInterceptorPoint {
    public TypeInterceptorPoint(ElementMatcher<? super TypeDescription> typeMatcher, AgentBuilder.Transformer transformer) {
        this.typeMatcher = typeMatcher;
        this.transformer = transformer;
    }

    private ElementMatcher<? super TypeDescription> typeMatcher;
    private AgentBuilder.Transformer transformer;

    public ElementMatcher<? super TypeDescription> getTypeMatcher() {
        return typeMatcher;
    }

    public void setTypeMatcher(ElementMatcher<? super TypeDescription> typeMatcher) {
        this.typeMatcher = typeMatcher;
    }

    public AgentBuilder.Transformer getTransformer() {
        return transformer;
    }

    public void setTransformer(AgentBuilder.Transformer transformer) {
        this.transformer = transformer;
    }
}