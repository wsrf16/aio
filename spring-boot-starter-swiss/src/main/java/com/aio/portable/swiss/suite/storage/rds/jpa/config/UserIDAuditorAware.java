package com.aio.portable.swiss.suite.storage.rds.jpa.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

//@Configuration
@EnableJpaAuditing
public class UserIDAuditorAware<T> implements AuditorAware<T> {
    @Override
    public Optional<T> getCurrentAuditor() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx == null || ctx.getAuthentication() == null || ctx.getAuthentication().getPrincipal() == null) {
            return Optional.ofNullable(null);
        }
        Object principal = ctx.getAuthentication().getPrincipal();
        if (principal.getClass().isAssignableFrom(Long.class)) {
            return Optional.ofNullable((T) principal);
        } else {
            return Optional.ofNullable(null);
        }
    }
}
