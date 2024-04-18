package com.aio.portable.swiss.suite.bytecode.bytebuddy;

import com.aio.portable.swiss.suite.bytecode.bytebuddy.interceptorpoint.MethodInterceptorPoint;
import com.aio.portable.swiss.suite.bytecode.bytebuddy.interceptorpoint.TypeInterceptorPoint;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.Annotation;
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
     * 功能，动态的给类属性添加注解
     *
     * @param className 类名
     * @param attributeName 类属性
     * @param typeName 注解类型
     */
    public static void addAnnotation(String className, String attributeName, String typeName) {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass ct = pool.get(className);
            CtField cf = ct.getField(attributeName);
            FieldInfo fieldInfo = cf.getFieldInfo();
            AnnotationsAttribute attribute = (AnnotationsAttribute) fieldInfo.getAttribute(AnnotationsAttribute.visibleTag);
            ConstPool cp = fieldInfo.getConstPool();
            Annotation annotation = new Annotation(typeName, cp);
            attribute.addAnnotation(annotation);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * attachInterceptor
     * @param agentBuilder
     * @param list
     * @return
     */
    public static final AgentBuilder attachInterceptor(AgentBuilder agentBuilder, Collection<TypeInterceptorPoint> list) {
        for (TypeInterceptorPoint item : list) {
            agentBuilder = agentBuilder.type(item.getTypeMatcher()).transform(item.getTransformer());
        }
        return agentBuilder;
    }



    /**
     * buildTransformer
     * @param list
     * @return
     */
    public static final AgentBuilder.Transformer buildTransformer(Collection<MethodInterceptorPoint> list) {
        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                    TypeDescription typeDescription,
                                                    ClassLoader classLoader,
                                                    JavaModule javaModule) {

                for (MethodInterceptorPoint item : list) {
                    builder = builder.method(item.getMethodMatcher())
                            .intercept(item.getImplementation());
                }

                return builder;
//                return AgentBuilderSugar.attachInterceptor(builder, list);
            }
        };
        return transformer;
    }


    /**
     * interceptorToAnyMethod
     * @param implementation
     * @param inst
     * @param elementMatcherCollection
     */
    public static final void interceptorToAnyMethod(Implementation implementation, Instrumentation inst, Collection<? extends ElementMatcher<? super TypeDescription>> elementMatcherCollection) {
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
     * @param elementMatchers
     */
    public static final void interceptorToAnyMethod(Implementation implementation, Instrumentation inst, ElementMatcher<? super TypeDescription>... elementMatchers) {
        Collection<ElementMatcher<? super TypeDescription>> elementMatcherCollection = Arrays.asList(elementMatchers);
        interceptorToAnyMethod(implementation, inst, elementMatcherCollection);
    }


        /**
         * buildListener
         * @return
         */
    public static final AgentBuilder.Listener buildListener() {
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
