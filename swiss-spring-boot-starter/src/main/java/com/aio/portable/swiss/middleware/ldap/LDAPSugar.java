package com.aio.portable.swiss.middleware.ldap;

import com.aio.portable.swiss.sugar.meta.ClassSugar;
import org.springframework.beans.BeanUtils;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.query.LdapQueryBuilder;

import javax.naming.NamingException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public abstract class LDAPSugar {
    public static final boolean authenticateByUId(LdapTemplate ldapTemplate, String base, String uid, String password) {
        EqualsFilter filter = new EqualsFilter("uid", uid);
        boolean authenticate = ldapTemplate.authenticate(base, filter.toString(), password);
        return authenticate;
    }

    public static final boolean authenticate(LdapTemplate ldapTemplate, String base, String filter, String password) {
        boolean authenticate = ldapTemplate.authenticate(base, filter, password);
        return authenticate;
    }


    /**
     * search
     * @param ldapTemplate
     * @param containerCriteria
     * @param mapper
     * @param <T>
     * @return
     */
    public static final <T> List<T> search(LdapTemplate ldapTemplate, ContainerCriteria containerCriteria, AttributesMapper<T> mapper) {
        List<T> list = ldapTemplate.search(containerCriteria, mapper);
        return list;
    }

    /**
     * search
     *
     * @param ldapTemplate
     * @param containerCriteria
     * @param clazz
     * @param t
     * @param <T>
     * @return
     */
    public static final <T> List<T> search(LdapTemplate ldapTemplate, ContainerCriteria containerCriteria, Class<T> clazz, T t) {
        List<T> list = ldapTemplate.search(containerCriteria, (AttributesMapper<T>) mapper -> {
            Map<String, Class<?>> nameClass = ClassSugar.PropertyDescriptors.toNameClassMap(clazz);
            nameClass.entrySet().forEach(prop -> {
                try {
                    String name = prop.getKey();
                    if (mapper.get(name) != null) {
                        Object val = mapper.get(name).get();
                        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, name);
                        propertyDescriptor.getWriteMethod().invoke(t, val);
                    }
                } catch (NamingException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
            return t;
        });
        return list;
    }


    /**
     * search
     *
     * @param ldapTemplate
     * @param containerCriteria
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> List<T> search(LdapTemplate ldapTemplate, ContainerCriteria containerCriteria, Class<T> clazz) {
        T t = ClassSugar.newInstance(clazz);
        return search(ldapTemplate, containerCriteria, clazz, t);
    }


    public static final <T> List<T> searchBySamAccountName(LdapTemplate ldapTemplate, String samAccountName, Class<T> clazz) {
        ContainerCriteria containerCriteria = LdapQueryBuilder.query()
                .where("sAMAccountName").is(samAccountName);

        List<T> search = LDAPSugar.search(ldapTemplate, containerCriteria, clazz);
        return search;
    }

    public static final List<LDAPAccount> searchBySamAccountName(LdapTemplate ldapTemplate, String samAccountName) {
        List<LDAPAccount> search = LDAPSugar.searchBySamAccountName(ldapTemplate, samAccountName, LDAPAccount.class);
        return search;
    }

}
