package com.aio.portable.swiss.sugar;

import com.aio.portable.swiss.structure.bean.BeanSugar;
import com.aio.portable.swiss.sugar.resource.ClassSugar;
import org.springframework.beans.BeanUtils;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.support.LdapUtils;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public abstract class LDAPSugar {
    /**
     * authentication
     * @param ldapTemplate
     * @param userName
     * @param password
     * @return
     */
    public final static Boolean authentication(LdapTemplate ldapTemplate, String userName, String password) {
        DirContext dirContext = null;
        Boolean b;
        try {
            dirContext = ldapTemplate.getContextSource().getContext(userName, password);
            b = true;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            b = false;
        } finally {
            if (dirContext != null)
                LdapUtils.closeContext(dirContext);
        }
        return b;
    }


    /**
     * search
     * @param ldapTemplate
     * @param containerCriteria
     * @param mapper
     * @param <T>
     * @return
     */
    public final static <T> List<T> search(LdapTemplate ldapTemplate, ContainerCriteria containerCriteria, AttributesMapper<T> mapper) {
        List<T> list = ldapTemplate.search(containerCriteria, mapper);
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
    public final static <T> List<T> search(LdapTemplate ldapTemplate, ContainerCriteria containerCriteria, Class<T> clazz) {
        List<T> list = ldapTemplate.search(containerCriteria, (AttributesMapper<T>) mapper -> {
            T t = ClassSugar.newInstance(clazz);
            Map<String, Class> nameClass = BeanSugar.PropertyDescriptors.getNameClass(clazz);

            nameClass.entrySet().forEach(prop -> {
                try {
                    String name = prop.getKey();
                    if (mapper.get(name) == null)
                        return;
                    Object val = mapper.get(name).get();
                    PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, name);
                    propertyDescriptor.getWriteMethod().invoke(t, val);
                } catch (NamingException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            });
            return t;
        });
        return list;
    }

//    public final static LdapContextSource newLdapContextSource(LdapProperties properties) {
//            LdapContextSource source = new LdapContextSource();
//            source.setUserDn(properties.getUsername());
//            source.setPassword(properties.getPassword());
//            source.setAnonymousReadOnly(properties.getAnonymousReadOnly());
//            source.setBase(properties.getBase());
//            source.setUrls(properties.getUrls());
//            source.setBaseEnvironmentProperties(Collections.unmodifiableMap(properties.getBaseEnvironment()));
//        return source;
//    }
//
//    public final static LdapTemplate newLdapTemplate(LdapContextSource contextSource) {
////        Map<String, Object> config = new HashMap();
////        config.put("java.naming.referral", "follow");
////        contextSource.setBaseEnvironmentProperties(config);
//        LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
//        return ldapTemplate;
//    }
}
