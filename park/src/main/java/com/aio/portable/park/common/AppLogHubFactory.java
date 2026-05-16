package com.aio.portable.park.common;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.log.solution.elk.kafka.KafkaLog;
import com.aio.portable.swiss.suite.log.solution.elk.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.support.LogHubUtils;

import java.util.List;

//@Component
public class AppLogHubFactory extends LogHubFactory {
//    @Override
//    public LogHub build(String className) {
//        return super.build(className).setAsync(false);
//    }

//    static {
//        new AppLogHubFactory();
//    }

    public static LogHub staticBuild(String name) {
        return LogHubFactory.staticBuild(name);
    }

    public static LogHub staticBuild(Class<?> clazz) {
        String className = clazz.getTypeName();
        return LogHubFactory.staticBuild(className);
    }

    public static LogHub staticBuild() {
        String className = StackTraceSugar.Previous.getClassName();
        return LogHubFactory.staticBuild(className);
    }

    public static LogHub staticBuild(int previous) {
        String className = StackTraceSugar.Previous.getClassName(previous);
        return LogHubFactory.staticBuild(className);
    }



    protected LogHub detectAndBuild(String name) {
        LogHub logHub = super.detectAndBuild(name);
        if (logHub != null) {
            if (LogHubUtils.Kafka.existDependency()) {
                List<LogSingle> list = logHub.getLogList();
                list.add(new KafkaLog(name, getProperties()));
            }
        }
        return logHub;
    }

    private static KafkaLogProperties getProperties() {
//        KafkaLogProperties kafkaLogProperties = new KafkaLogProperties();
        KafkaLogProperties kafkaLogProperties = KafkaLogProperties.getSingleton();
        return kafkaLogProperties;
    }




}
