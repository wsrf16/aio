package com.aio.portable.park.common;

import com.aio.portable.swiss.assist.log.hub.factory.classic.Slf4jHubFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomLogHubFactory extends Slf4jHubFactory { //KafkaHubFactory {
}