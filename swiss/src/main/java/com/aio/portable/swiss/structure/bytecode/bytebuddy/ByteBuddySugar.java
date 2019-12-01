package com.aio.portable.swiss.structure.bytecode.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

public abstract class ByteBuddySugar {
//    public final static ClassLoadingStrategy.Default wrapper;
    final static ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();



//    static {
//        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//        classLoader = Thread.currentThread().getContextClassLoader();
//        ClassReloadingStrategy classLoadingStrategy = ClassReloadingStrategy.fromInstalledAgent();
//        ClassLoadingStrategy.Default wrapper = ClassLoadingStrategy.Default.WRAPPER;
//        TypePool typePool = TypePool.Default.of(classLoader);
//        ConstructorStrategy.Default noConstructors = ConstructorStrategy.Default.NO_CONSTRUCTORS;
//    }

    /**
     * redefineClass
     * @param sourceClazz
     * @param targetQualifiedClassName "com.sandbox.console.Foo1"
     * @param <S>
     */
    public final static <S> void redefineClass(String targetQualifiedClassName, Class<S> sourceClazz) throws ClassNotFoundException {
        ClassReloadingStrategy fromInstalledAgent = ClassReloadingStrategy.fromInstalledAgent();

        DynamicType.Builder<S> builder = new ByteBuddy()
                .redefine(sourceClazz)
//                .implement(MInterface.class)
                .name(targetQualifiedClassName)
//                .modifiers(Visibility.PUBLIC)
//                .defineConstructor(Visibility.PUBLIC)
//                .intercept(MethodCall.invoke(Bar.class.getDeclaredConstructor()))
                ;
        DynamicType.Unloaded<S> unloaded = builder.make();
//        DynamicType.Loaded<S> load = unloaded.load(Class.forName(targetQualifiedClassName).getClassLoader(), fromInstalledAgent);
        DynamicType.Loaded<S> load = unloaded.load(sourceClazz.getClassLoader(), fromInstalledAgent);
//        Class<? extends S> toClazz = load.getLoaded();
    }

    /**
     * redefineClass
     * @param sourceClazz
     * @param targetClazz
     * @param <S>
     * @param <T>
     */
    public final static <S, T> void redefineClass(Class<T> targetClazz, Class<S> sourceClazz) throws ClassNotFoundException {
        redefineClass(targetClazz.getName(), sourceClazz);
    }


    /**
     * redefineMethod
     * @param method ElementMatchers.named("toString")
     * @param implementation FixedValue.value("Hello World!")   MethodDelegation.to(Log4j.class)
     */
    public final static void redefineMethod(Class<?> clazz, ElementMatcher<? super MethodDescription> method, Implementation implementation) {
        ClassReloadingStrategy fromInstalledAgent = ClassReloadingStrategy.fromInstalledAgent();
        Class<?> dynamicType = new ByteBuddy()
                .redefine(clazz)
                .method(method)
                .intercept(implementation)
                .make()
                .load(clazz.getClassLoader(), fromInstalledAgent)
                .getLoaded();

    }


}
