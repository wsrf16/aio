package com.aio.portable.park.common;

import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.log.impl.console.ConsoleLog;
import com.aio.portable.swiss.suite.log.impl.es.ESLogNote;
import com.aio.portable.swiss.suite.log.impl.es.kafka.KafkaLog;
import com.aio.portable.swiss.suite.log.impl.es.rabbit.RabbitMQLog;
import com.aio.portable.swiss.suite.log.impl.slf4j.Slf4jLog;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogNote;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class AppLogHubFactory extends LogHubFactory {
    public static class NanJingESLogNote extends ESLogNote {
        public String getModelName() {
            return super.getEsIndex();
        }

        public void setModelName(String modelName) {
            super.setEsIndex(modelName);
        }

        public NanJingESLogNote(ESLogNote esLogNote) {
            BeanSugar.Cloneable.deepCopy(esLogNote, this);
        }
    }

    @Override
    public LogHub build(String className) {
        final RabbitMQLog rabbitMQLog1 = new RabbitMQLog(className);
        final RabbitMQLog rabbitMQLog2 = new RabbitMQLog(){
            @Override
            public ESLogNote convert(LogNote logNote) {
                ESLogNote origin = super.convert(logNote);
                ESLogNote convert = new NanJingESLogNote(origin);
                return convert;
            }
        };


        LogHub logger = LogHub.build(
//                new KafkaLog(className),
                rabbitMQLog1,
                rabbitMQLog2,
//                new ConsoleLog(className),
                new Slf4jLog(className)
                )
                .setEnabledLevel(LevelEnum.INFORMATION)
                ;

        return logger;
    }
}
