package com.aio.portable.swiss.suite.log.support;

import com.aio.portable.swiss.suite.resource.ClassSugar;

public class LogHubUtils {
    public static class Kafka {
        public final static String DEPENDENCY = "org.springframework.kafka.core.KafkaTemplate";
        public static boolean existDependency() {
            return ClassSugar.exist(DEPENDENCY);
        }
    }

    public static class RabbitMQ {
        public final static String DEPENDENCY = "org.springframework.amqp.rabbit.core.RabbitTemplate";

        public static boolean existDependency() {
            return ClassSugar.exist(DEPENDENCY);
        }
    }


}
