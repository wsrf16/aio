package com.aio.portable.swiss.suite.storage.rds.jpa.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
public class UserIDAuditorAware<T> implements AuditorAware<T> {
    @Override
    public Optional<T> getCurrentAuditor() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Object principal;
        if (ctx == null || ctx.getAuthentication() == null || ctx.getAuthentication().getPrincipal() == null) {
            principal = null;
        } else {
            principal = ctx.getAuthentication().getPrincipal();
        }
        return Optional.ofNullable((T) principal);
    }
}
