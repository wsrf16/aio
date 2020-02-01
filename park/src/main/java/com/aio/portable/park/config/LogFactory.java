package com.aio.portable.park.config;

import com.aio.portable.swiss.structure.log.base.factory.classic.RabbitMQHubFactory;
import com.aio.portable.swiss.structure.log.base.factory.classic.Slf4jHubFactory;
import org.springframework.stereotype.Component;

//RabbitMQHubFactory {
@Component
public class LogFactory extends RabbitMQHubFactory {
}