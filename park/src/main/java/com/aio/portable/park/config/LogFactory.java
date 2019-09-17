package com.aio.portable.park.config;

import com.aio.portable.swiss.assist.log.hub.factory.classic.Slf4jHubFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogFactory extends Slf4jHubFactory { //KafkaHubFactory {
}