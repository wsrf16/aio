package com.york.portable.park.common;

import com.york.portable.swiss.assist.log.hub.factory.classic.Slf4jHubFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomLogHubFactory extends Slf4jHubFactory { //KafkaHubFactory {
}