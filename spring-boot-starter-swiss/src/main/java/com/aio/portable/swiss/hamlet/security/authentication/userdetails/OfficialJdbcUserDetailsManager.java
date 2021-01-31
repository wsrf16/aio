package com.aio.portable.swiss.hamlet.security.authentication.userdetails;

import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

public abstract class OfficialJdbcUserDetailsManager extends JdbcUserDetailsManager {
//    @Bean
//    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }
    public OfficialJdbcUserDetailsManager() {
    }

    public OfficialJdbcUserDetailsManager(DataSource dataSource) {
        super(dataSource);
    }
}
