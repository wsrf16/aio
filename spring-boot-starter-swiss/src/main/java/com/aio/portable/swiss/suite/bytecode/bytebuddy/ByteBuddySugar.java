package com.aio.portable.swiss.suite.bytecode.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.matcher.ElementMatcher;

public abstract class ByteBuddySugar {
//    public static final ClassLoadingStrategy.Default wrapper;
    static final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();



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
     * @param targetCompleteClassName "com.sandbox.console.Foo1"
     * @param <S>
     */
    public static final <S> void redefineClass(String targetCompleteClassName, Class<S> sourceClazz) throws ClassNotFoundException {
        ClassReloadingStrategy fromInstalledAgent = ClassReloadingStrategy.fromInstalledAgent();

        DynamicType.Builder<S> builder = new ByteBuddy()
                .redefine(sourceClazz)
//                .implement(MInterface.class)
                .name(targetCompleteClassName)
//                .modifiers(Visibility.PUBLIC)
//                .defineConstructor(Visibility.PUBLIC)
//                .intercept(MethodCall.invoke(Bar.class.getDeclaredConstructor()))
                ;
        DynamicType.Unloaded<S> unloaded = builder.make();
//        DynamicType.Loaded<S> load = unloaded.load(Class.forName(targetCompleteClassName).getClassLoader(), fromInstalledAgent);
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
    public static final <S, T> void redefineClass(Class<T> targetClazz, Class<S> sourceClazz) throws ClassNotFoundException {
        redefineClass(targetClazz.getName(), sourceClazz);
    }


    /**
     * redefineMethod
     * @param method ElementMatchers.named("toString")
     * @param implementation FixedValue.value("Hello World!")   MethodDelegation.to(Log4j.class)
     */
    public static final void redefineMethod(Class<?> clazz, ElementMatcher<? super MethodDescription> method, Implementation implementation) {
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
