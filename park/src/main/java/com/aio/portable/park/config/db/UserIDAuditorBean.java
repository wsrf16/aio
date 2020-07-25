package com.aio.portable.park.config.db;

import com.aio.portable.swiss.suite.storage.rds.jpa.config.UserIDAuditorAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserIDAuditorBean extends UserIDAuditorAware<Long> {

}
