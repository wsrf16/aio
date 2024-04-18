package com.aio.portable.swiss.suite.storage.db.jpa.config;

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
        T principal;
        if (ctx == null || ctx.getAuthentication() == null || ctx.getAuthentication().getPrincipal() == null) {
            principal = null;
        } else {
            principal = (T) ctx.getAuthentication().getPrincipal();
        }
//        return null;
        return Optional.ofNullable(principal);
    }
}
