package com.aio.portable.swiss.hamlet.security.authorization.user;

import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import javax.sql.DataSource;

public abstract class OfficialJdbcUserDetailsService extends JdbcDaoImpl {
    public OfficialJdbcUserDetailsService(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
}
