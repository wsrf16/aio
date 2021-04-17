//package com.aio.portable.swiss.hamlet.security.authentication;
//
//import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public abstract class JDBCAuthenticationProvider implements AuthenticationProvider {
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String name = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        UserDetails user = loadUserByUsername(name);
//        if (user == null || !user.getPassword().equals(password)) {
//            throw new AuthenticationCredentialsNotFoundException("invalid username or password.");
//        }
//
//        return new UsernamePasswordAuthenticationToken(name, password, user.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//
//    public abstract UserDetails loadUserByUsername(String name);
//}
