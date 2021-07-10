package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.bytecode.bytebuddy.ByteBuddySugar;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class ByteBuddyTest {
    public static class Bar  {
        public static String name = "bar";
        public static String m() { return name; }
    }
    public static class Foo  {
        public static String name = "foo";
        public static String m() { return name; }
    }

    @Test
    public void foobar() throws ClassNotFoundException {
        ByteBuddyAgent.install();
        ByteBuddySugar.redefineMethod(Foo.class, ElementMatchers.named("m"), MethodDelegation.to(Bar.class));
        System.out.println(new Foo().m());

        ByteBuddySugar.redefineClass(Foo.class, Bar.class);
        System.out.println(new Foo().m());
    }
}
