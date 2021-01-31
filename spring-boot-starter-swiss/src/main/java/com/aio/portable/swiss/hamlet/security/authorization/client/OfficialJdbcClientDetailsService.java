package com.aio.portable.swiss.hamlet.security.authorization.client;

import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * OfficialJdbcClientDetailsService
 * Method "configure(ClientDetailsServiceConfigurer clients)" In AuthorizationServerConfigurerAdapter.class
 */
public abstract class OfficialJdbcClientDetailsService extends JdbcClientDetailsService {
    public OfficialJdbcClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }

//    @Bean
//    public ClientDetailsService jdbcClientDetailsService(DataSource dataSource) throws Exception {
////        return new JdbcClientDetailsService(dataSource);
//        return new JdbcClientDetailsServiceBuilder().dataSource(dataSource).build();
//    }
}