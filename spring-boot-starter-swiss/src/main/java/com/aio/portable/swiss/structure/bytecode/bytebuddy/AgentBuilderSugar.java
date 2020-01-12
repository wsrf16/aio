package com.aio.portable.swiss.structure.bytecode.bytebuddy;

import com.aio.portable.swiss.structure.bytecode.bytebuddy.interceptorpoint.MethodInterceptorPoint;
import com.aio.portable.swiss.structure.bytecode.bytebuddy.interceptorpoint.TypeInterceptorPoint;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public abstract class AgentBuilderSugar {
    /**
     * attachInterceptor
     * @param agentBuilder
     * @param typeInterceptorPointCollection
     * @return
     */
    public final static AgentBuilder attachInterceptor(AgentBuilder agentBuilder, Collection<TypeInterceptorPoint> typeInterceptorPointCollection) {
        for (TypeInterceptorPoint item : typeInterceptorPointCollection) {
            agentBuilder = agentBuilder.type(item.getTypeMatcher()).transform(item.getTransformer());
        }
        return agentBuilder;
    }


    /**
     * attachInterceptor
     * @param builder
     * @param methodInterceptorPointCollection
     * @param <T>
     * @return
     */
    public final static <T> DynamicType.Builder<T> attachInterceptor(DynamicType.Builder<T> builder, Collection<MethodInterceptorPoint> methodInterceptorPointCollection) {
        for (MethodInterceptorPoint item : methodInterceptorPointCollection) {
            builder = builder.method(item.getMethodMatcher()).intercept(item.getImplementation());
        }
        return builder;
    }


    /**
     * buildTransformer
     * @param methodInterceptorPointCollection
     * @return
     */
    public final static AgentBuilder.Transformer buildTransformer(Collection<MethodInterceptorPoint> methodInterceptorPointCollection) {
        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                    TypeDescription typeDescription,
                                                    ClassLoader classLoader,
                                                    JavaModule javaModule) {
                return AgentBuilderSugar.attachInterceptor(builder, methodInterceptorPointCollection);
            }
        };
        return transformer;
    }


//    /**
//     * interceptorToMethodInterceptorPoint
//     * @param methodInterceptorPointCollection
//     * @param inst
//     * @param elementMatcherCollection
//     */
//    public final static void interceptorToMethodInterceptorPoint(Collection<MethodInterceptorPoint> methodInterceptorPointCollection, Instrumentation inst, Collection<? extends ElementMatcher<? super TypeDescription>> elementMatcherCollection) {
//        AgentBuilder.Transformer transformer = buildTransformer(methodInterceptorPointCollection);
//
//        Collection<TypeInterceptorPoint> typeInterceptorCollection = new ArrayList<>();
//        typeInterceptorCollection.addAll(elementMatcherCollection.stream().map(c -> new TypeInterceptorPoint(c, transformer)).collect(Collectors.toList()));
//
//        AgentBuilder.Listener listener = buildListener();
//        AgentBuilderWorld.attachInterceptor(new AgentBuilder.Default(), typeInterceptorCollection)
//                .with(listener)
//                .installOn(inst);
//    }

    /**
     * interceptorToAnyMethod
     * @param implementation
     * @param inst
     * @param elementMatcherCollection
     */
    public final static void interceptorToAnyMethod(Implementation implementation, Instrumentation inst, Collection<? extends ElementMatcher<? super TypeDescription>> elementMatcherCollection) {
        Collection<MethodInterceptorPoint> methodInterceptorPointCollection = new ArrayList<>();
        methodInterceptorPointCollection.add(new MethodInterceptorPoint(ElementMatchers.<MethodDescription>any(), implementation));

        AgentBuilder.Transformer transformer = buildTransformer(methodInterceptorPointCollection);

        Collection<TypeInterceptorPoint> typeInterceptorCollection = new ArrayList<>();
        typeInterceptorCollection.addAll(elementMatcherCollection.stream().map(c -> new TypeInterceptorPoint(c, transformer)).collect(Collectors.toList()));

        AgentBuilder.Listener listener = buildListener();
        AgentBuilderSugar.attachInterceptor(new AgentBuilder.Default(), typeInterceptorCollection)
                .with(listener)
                .installOn(inst);
    }


    /**
     * interceptorToAnyMethod
     * @param implementation
     * @param inst
     * @param elementMatcherArray
     */
    public final static void interceptorToAnyMethod(Implementation implementation, Instrumentation inst, ElementMatcher<? super TypeDescription>... elementMatcherArray) {
        Collection<ElementMatcher<? super TypeDescription>> elementMatcherCollection = Arrays.asList(elementMatcherArray);
        interceptorToAnyMethod(implementation, inst, elementMatcherCollection);
    }


        /**
         * buildListener
         * @return
         */
    public final static AgentBuilder.Listener buildListener() {
        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
                if (1 == 1)
                    return;
            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean bool, DynamicType dynamicType) {
                if (1 == 1)
                    return;
            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean bool) {
                if (1 == 1)
                    return;
            }

            @Override
            public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean bool, Throwable throwable) {
                if (1 == 1)
                    return;
            }

            @Override
            public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean bool) {
                if (1 == 1)
                    return;
            }
        };
        return listener;
    }
}
