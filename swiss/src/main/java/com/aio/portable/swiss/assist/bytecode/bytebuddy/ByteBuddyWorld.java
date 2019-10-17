package com.aio.portable.swiss.assist.bytecode.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

public class ByteBuddyWorld {
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
     * fake
     *
     * @param sourceClazz
     * @param targetQualifiedClassName "com.sandbox.console.Foo1"
     * @param <S>
     */
    public final static <S> void rewriteClass(String targetQualifiedClassName, Class<S> sourceClazz) throws ClassNotFoundException {
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
     * fake
     * @param sourceClazz
     * @param targetClazz
     * @param <S>
     * @param <T>
     */
    public final static <S, T> void rewriteClass(Class<T> targetClazz, Class<S> sourceClazz) throws ClassNotFoundException {
        rewriteClass(targetClazz.getName(), sourceClazz);
    }


    /**
     * rewrite
     * @param method ElementMatchers.named("toString")
     * @param implementation FixedValue.value("Hello World!")   MethodDelegation.to(Log4j.class)
     */
    public final static void rewriteMethod(Class<?> clazz, ElementMatcher<? super MethodDescription> method, Implementation implementation) {
        ClassReloadingStrategy fromInstalledAgent = ClassReloadingStrategy.fromInstalledAgent();
        Class<?> dynamicType = new ByteBuddy()
                .redefine(clazz)
                .method(method)
                .intercept(implementation)
                .make()
                .load(clazz.getClassLoader(), fromInstalledAgent)
                .getLoaded();

    }

    private static class BlahUnit {
        public static class Bar  {
            public static String name = "bar";
            public static String m() { return name; }
        }
        public static class Foo  {
            public static String name = "foo";
            public static String m() { return name; }
        }

        private static void todo() throws ClassNotFoundException {
            ByteBuddyWorld.rewriteMethod(Foo.class, ElementMatchers.named("m"), MethodDelegation.to(Bar.class));
            System.out.println(new Foo().m());

            ByteBuddyWorld.rewriteClass(Foo.class, Bar.class);
            System.out.println(new Foo().m());
        }
    }
}
