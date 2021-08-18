package com.aio.portable.swiss.factories.listener.propertysource;

import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.stream.StreamSupport;

public class PropertySourcesMutator {

    PropertySourceFilter filter;
    PropertySourceReplace replace;
    PropertySourceItemReplace itemReplace;

    public void setFilter(PropertySourceFilter filter) {
        this.filter = filter;
    }

    public void setReplace(PropertySourceReplace replace) {
        this.replace = replace;
    }

    public void setItemReplace(PropertySourceItemReplace itemReplace) {
        this.itemReplace = itemReplace;
    }

    public PropertySourcesMutator() {
//        this.match = c -> c instanceof OriginTrackedMapPropertySource || c instanceof CompositePropertySource;
//        this.itemReplace = (key, value) -> new KeyValuePair<>(key, value);
//        this.mapReplace = m -> {};
    }

    public boolean filter(PropertySource<?> propertySource) {
        return filter.test(propertySource);
    }



    public void replace(PropertySource<?> propertySource) {
        this.replace.accept(propertySource);
//        if (propertySource instanceof OriginTrackedMapPropertySource) {
//            final Map<String, OriginTrackedValue> map = (HashMap) (propertySource.getSource());
//            map.entrySet().stream().forEach(c -> {
//                String key = c.getKey();
//                // "applicationConfig: [classpath:/config/application-dev.yml]"
//                Origin origin = c.getValue().getOrigin();
//                Object value = c.getValue().getValue();
//                KeyValuePair<String, Object> targetPair = itemReplace.apply(key, value);
//                String targetKey = targetPair.getKey();
//                Object targetValue = targetPair.getValue();
//
//                if (value != targetValue)
//                    map.put(targetKey, OriginTrackedValue.of(targetValue, origin));
//            });
////            CompositePropertySource source = (((MutablePropertySources) propertySources).propertySourceList).get(0)
//
//        }
    }

    public void mutate(MutablePropertySources propertySources) {
        StreamSupport.stream(propertySources.spliterator(), false)
//        propertySources.stream()
                .filter(c -> this.filter(c))
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
