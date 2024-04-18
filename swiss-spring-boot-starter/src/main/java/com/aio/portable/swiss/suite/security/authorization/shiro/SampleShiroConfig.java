package com.aio.portable.swiss.suite.security.authorization.shiro;

import com.aio.portable.swiss.suite.security.authorization.shiro.cache.HashMapCacheManager;
import com.aio.portable.swiss.suite.security.authorization.shiro.filter.HamletBearerAuthenticationFilter;
import com.aio.portable.swiss.suite.security.authorization.shiro.realm.impl.BearerTokenRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SampleShiroConfig {
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //设置session过期时间
        sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 去掉shiro登录时url里的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(true);
        return sessionManager;
    }

    @Bean
    public HashMapCacheManager hashMapCacheManager() {
        return new HashMapCacheManager();
    }

    @Bean
    public DefaultWebSecurityManager securityManager(BearerTokenRealm authorizingRealm, HashMapCacheManager hashMapCacheManager, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authorizingRealm);
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);

        securityManager.setSubjectDAO(subjectDAO);
        securityManager.setCacheManager(hashMapCacheManager);
        securityManager.setSessionManager(sessionManager);

        return securityManager;
    }


//    @Autowired
//    RolesAuthorizationFilter rolesFilter;

    @Bean
    public HamletBearerAuthenticationFilter bearerAuthFilter() {
        return new HamletBearerAuthenticationFilter();
    }

    @Autowired
    public HamletBearerAuthenticationFilter bearerAuthFilter;

    /**
     * Filter Name     Class
     * anon            org.apache.shiro.web.filter.authc.AnonymousFilter
     * authc           org.apache.shiro.web.filter.authc.FormAuthenticationFilter
     * authcBasic      org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
     * perms           org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter perms[user:add]
     * port            org.apache.shiro.web.filter.authz.PortFilter
     * rest            org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
     * roles           org.apache.shiro.web.filter.authz.RolesAuthorizationFilter roles[admin,boss]
     * ssl             org.apache.shiro.web.filter.authz.SslFilter
     * user            org.apache.shiro.web.filter.authc.UserFilter
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setUnauthorizedUrl("/account/unAuth");
        shiroFilterFactoryBean.setLoginUrl("/account/login");
        shiroFilterFactoryBean.setSuccessUrl("/account/userInfo");


//        org.apache.shiro.web.filter.mgt.DefaultFilter
//        filters = new DefaultFilterChainManager().getFilters();
        String jwt = "jwt";
        Map<String, Filter> filters = new HashMap<>();
        filters.put(jwt, bearerAuthFilter);
        shiroFilterFactoryBean.setFilters(filters);

        // 配置拦截器（即“endpoint”与“filter”的对应关系，注意顺序）
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/account/login", "anon");
        filterChainDefinitionMap.put("/account/logout", "logout");
        // 若与注解冲突，以and取合集作为判断标准
//        filterChainDefinitionMap.put("/user/updateRequireRoles", "jwt, roles");
//        filterChainDefinitionMap.put("/user/updateRequirePermissions", "jwt, perms");
        filterChainDefinitionMap.put("/user/updateRequireRoles", "jwt, roles[teacher]");
        filterChainDefinitionMap.put("/user/updateRequirePermissions", "jwt, perms[user:update]");

        filterChainDefinitionMap.put("/**", jwt);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    /**
     * 添加注解支持
     */
    @Bean
    public FilterRegistrationBean delegatingFilterProxy() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilterFactoryBean");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    /**
     * 添加注解支持
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 开启shiro 注解支持. 使以下注解能够生效 :
     * 需要认证 {@link org.apache.shiro.authz.annotation.RequiresAuthentication RequiresAuthentication}
     * 需要用户 {@link org.apache.shiro.authz.annotation.RequiresUser RequiresUser}
     * 需要访客 {@link org.apache.shiro.authz.annotation.RequiresGuest RequiresGuest}
     * 需要角色 {@link org.apache.shiro.authz.annotation.RequiresRoles RequiresRoles}
     * 需要权限 {@link org.apache.shiro.authz.annotation.RequiresPermissions RequiresPermissions}
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
//
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
}