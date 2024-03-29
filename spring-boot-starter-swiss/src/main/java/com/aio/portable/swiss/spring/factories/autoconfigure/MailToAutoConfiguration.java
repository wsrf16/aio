package com.aio.portable.swiss.spring.factories.autoconfigure;

import com.aio.portable.swiss.suite.net.mail.MailTo;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;

import javax.activation.MimeType;
import javax.mail.internet.MimeMessage;

//@Import(JavaMailSenderImpl.class)
@AutoConfigureAfter(MailSenderAutoConfiguration.class)
@ConditionalOnClass({MimeMessage.class, MimeType.class, MailSender.class, JavaMailSender.class})
//@ConditionalOnMissingBean({MailSender.class})
@EnableConfigurationProperties({MailProperties.class})
public class MailToAutoConfiguration {

    @Bean
    @ConditionalOnBean(JavaMailSender.class)
    public MailTo mailUtils(JavaMailSender javaMailSender) {
        return new MailTo(javaMailSender);
    }

}
