package com.aio.portable;

import com.aio.portable.swiss.suite.bytecode.bytebuddy.AgentBuilderSugar;
import com.aio.portable.swiss.suite.bytecode.bytebuddy.interceptorpoint.MethodInterceptorPoint;
import com.aio.portable.swiss.suite.bytecode.bytebuddy.interceptorpoint.TypeInterceptorPoint;
import com.aio.portable.swiss.suite.bytecode.bytebuddy.sample.annotation.JavaAgentInterceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Collection;


public class PreMain {
    public static void main(String[] args) {
        System.out.println("Hello World! PreMain.main");
    }


    public static void sample(Instrumentation inst) {
        // step one: Interceptors
        Collection<MethodInterceptorPoint> methodList = new ArrayList<>();
        methodList.add(new MethodInterceptorPoint(ElementMatchers.<MethodDescription>any(), MethodDelegation.to(CustomInterceptor.class)));
        methodList.add(new MethodInterceptorPoint(ElementMatchers.<MethodDescription>named("statichello"), MethodDelegation.to(CustomInterceptor.class)));
        AgentBuilder.Transformer transformer = AgentBuilderSugar.buildTransformer(methodList);

        // step two: Classes to intercept
        Collection<TypeInterceptorPoint> typeList = new ArrayList<>();
        typeList.add(new TypeInterceptorPoint(ElementMatchers.named("com.mysql.cj.jdbc.ConnectionImpl"), transformer));
        typeList.add(new TypeInterceptorPoint(ElementMatchers.named("java.sql.DriverManager"), transformer));
        typeList.add(new TypeInterceptorPoint(ElementMatchers.nameStartsWith("java.sql"), transformer));
        typeList.add(new TypeInterceptorPoint(ElementMatchers.nameStartsWith("com.aio.portable.park.runner"), transformer));

        AgentBuilder agentBuilder = new AgentBuilder.Default();
        for (TypeInterceptorPoint item : typeList) {
            agentBuilder = agentBuilder.type(item.getTypeMatcher()).transform(item.getTransformer());
        }

    }







    public static final void premain(String agentOps, Instrumentation inst)
    {
        System.out.println("Hello World! PreMain.premain(String agentOps, Instrumentation inst)");
        System.out.println(agentOps);


//        inst.addTransformer(new MyTransformer());

        AgentBuilderSugar.interceptorToAnyMethod(MethodDelegation.to(CustomInterceptor.class).andThen(MethodDelegation.to(MonitorInterceptor.class)), inst,
//        AgentBuilderWorld.interceptorToAnyMethod(MethodDelegation.to(CustomInterceptor.class), inst,
                ElementMatchers.named("com.mysql.cj.jdbc.ConnectionImpl"),
                ElementMatchers.named("java.sql.DriverManager"),
                ElementMatchers.nameStartsWith("java.sql"),
                ElementMatchers.nameStartsWith("com.sandbox.console.buddy.sample"),
                ElementMatchers.isAnnotatedWith(JavaAgentInterceptor.class)
        );
    }






    public static void premain(String agentOps)
    {
        System.out.println("Hello World! PreMain.premain(String agentOps)");
        System.out.println(agentOps);
    }

}
