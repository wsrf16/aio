package com.aio.portable.swiss.suite.properties;

import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

public class PropertySourcesMutator {

    PropertySourceMatcher matcher;
    PropertySourceResolver resolver;

    public void setMatcher(PropertySourceMatcher matcher) {
        this.matcher = matcher;
    }

    public void setResolver(PropertySourceResolver resolver) {
        this.resolver = resolver;
    }


    public PropertySourcesMutator() {
        this.resolver = (name, value) -> value;
        this.matcher = c -> c instanceof OriginTrackedMapPropertySource;
    }

    public PropertySourcesMutator(PropertySourceResolver resolver) {
        this.resolver = resolver;
        this.matcher = c -> c instanceof OriginTrackedMapPropertySource;
    }

    public boolean match(PropertySource<?> propertySource) {
        return matcher.test(propertySource);
    }



    public void replace(PropertySource<?> propertySource) {
        final Map<String, OriginTrackedValue> map = (HashMap) (propertySource.getSource());
        map.entrySet().stream().forEach(c -> {
            String name = c.getKey();
            // "applicationConfig: [classpath:/config/application-dev.yml]"
            Origin origin = c.getValue().getOrigin();
            Object value = c.getValue().getValue();
            Object targetValue = resolver.apply(name, value);

            if (value != targetValue)
                map.put(name, OriginTrackedValue.of(targetValue, origin));
        });


//        final LinkedHashMap<String, OriginTrackedValue> map = (LinkedHashMap) (propertySource.getSource());
//
//        if (map.containsKey("swagger.api-info.title")) {
//            OriginTrackedValue originTrackedValue = map.get("swagger.api-info.title");
//            final Origin origin = originTrackedValue.getOrigin();
//            final Object value = originTrackedValue.getValue();
//
////                String value = (String)map.get("swagger.api-info.title");
////                String value = (String)originTrackedValue.getValue();
////                value = value + "3333333333333";
////                map.put("swagger.api-info.title", value);
//            System.out.println(value + "3333333333333");
//            map.put("swagger.api-info.title", OriginTrackedValue.of(value + "33333333333", origin));
//        }
    }

    public void mutate(PropertySources propertySources) {
        StreamSupport.stream(propertySources.spliterator(), false)
                .filter(c -> this.match(c))
                .forEach(c -> this.replace(c));
    }

//    public PropertySource<?> proxy(PropertySource<?> propertySource) {
//        if (Objects.equals(resolver, 1))
//            return propertySource;
//        else {
////                PropertySource proxy = new PropertySource(propertySource.getName(), propertySource.getSource()) {
////
////                    @Override
////                    public Object getProperty(String name) {
////                        if (propertySource.getSource().equals("对外接口在线文档"))
////                            return propertySource.getProperty(name) + "11111111";
////                        else
////                            return propertySource.getProperty(name);
////                    }
////                };
//
//            if (propertySource.getClass().getName().equals("org.springframework.boot.context.properties.source.ConfigurationPropertySourcesPropertySource")) {
//                final Object proxy = ProxySugar.proxyFactory(propertySource, new MethodInterceptor() {
//                    @Override
//                    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
//                        if (methodInvocation.getMethod().getName().equals("getProperty")) {
//                            System.out.println("beforeeeeeeeeeeeeeee");
//                            Object proceed = methodInvocation.proceed();
//                            proceed += "qianruuuuuuuuuuuuuuuuuuu";
//                            System.out.println("afterrrrrrrrrrrrrrrr");
//                            return proceed;
//                        } else {
////                            final Method method = methodInvocation.getMethod();
////                            method.getParameters()
//                            Object proceed = methodInvocation.proceed();
//                            return proceed;
//                        }
//                    }
//                });
//                return (PropertySource<?>) proxy;
//            } else {
//                return propertySource;
//            }
//
////                PropertySource proxy = ProxySugar.cglibProxy(propertySource.getClass(), new MethodInterceptor() {
////                    @Override
////                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
////                        if (method.getName().equals("getProperty") && objects.length == 1 && objects instanceof String[]) {
////                            String name = (String)objects[0];
////                            Object value = propertySource.getProperty(name);
////
////                            if (value.equals("对外接口在线文档")){
//////                            method.invoke(propertySource, name);
////                                value = value + "222222222222";
////                            }
////                            return value;
////                        } else {
////                            String name = (String)objects[0];
////                            return propertySource.getProperty(name);
////                        }
////                    }
////                });
////                return proxy;
//        }
//    }
}
