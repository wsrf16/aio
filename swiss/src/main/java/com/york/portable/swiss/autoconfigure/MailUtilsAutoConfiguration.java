package com.york.portable.swiss.autoconfigure;

import com.york.portable.swiss.sugar.MailUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.activation.MimeType;
import javax.mail.internet.MimeMessage;

//@Configuration
//@Import(JavaMailSenderImpl.class)
@AutoConfigureAfter(MailSenderAutoConfiguration.class)

@ConditionalOnClass({MimeMessage.class, MimeType.class, MailSender.class})
//@ConditionalOnMissingBean({MailSender.class})
@EnableConfigurationProperties({MailProperties.class})
public class MailUtilsAutoConfiguration {

    @Bean
    @ConditionalOnBean(JavaMailSender.class)
    public MailUtils mailUtils(JavaMailSender javaMailSender) {
        return new MailUtils(javaMailSender);
    }

}
