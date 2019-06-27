package com.york.portable.swiss.autoconfigure;

import com.york.portable.swiss.sugar.MailUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
//@Import(JavaMailSenderImpl.class)
@AutoConfigureAfter(MailSenderAutoConfiguration.class)
public class MailUtilsAutoConfiguration {

    @Bean
    @ConditionalOnBean(JavaMailSender.class)
    public MailUtils mailUtils(JavaMailSender javaMailSender) {
        return new MailUtils(javaMailSender);
    }

}
