package com.aio.portable.swiss.spring.factories.listener;

import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.encrypt.TextEncryptor;

// GenericApplicationListener
// SmartApplicationListener
// ConfigFileApplicationListener
public class RegisterTextEncryptorListener extends AbstractGenericApplicationListener implements Ordered {
    // EnvironmentDecryptApplicationInitializer
    // EncryptionBootstrapConfiguration
    // FailsafeTextEncryptor
    public static final int DEFAULT_ORDER = Integer.MIN_VALUE + 10;
    private static final Class<?>[] EVENT_TYPES = {
            ApplicationStartingEvent.class,
            ApplicationEnvironmentPreparedEvent.class,
            ApplicationPreparedEvent.class,
            ContextClosedEvent.class,
            ApplicationFailedEvent.class,
            ApplicationReadyEvent.class,
            ApplicationStartedEvent.class,
            ContextRefreshedEvent.class,
            ApplicationContextInitializedEvent.class
    };
//    private static final Log log = LogFactory.getLog(SwissApplicationListener.class);
    private static final LocalLog log = LocalLog.getLog(RegisterTextEncryptorListener.class);
    private int order = DEFAULT_ORDER;

    // step 4: An ApplicationPreparedEvent is sent just before the refresh is started, but after bean definitions have been loaded.
    @Override
    protected void onApplicationPreparedEvent(ApplicationPreparedEvent event) {
        if (RegisterTextEncryptorListener.textEncryptor != null) {
            ConfigurableApplicationContext applicationContext = event.getApplicationContext();
            if (applicationContext instanceof AnnotationConfigApplicationContext) {
                ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
                if (!beanFactory.containsBean("textEncryptor")) {
                    beanFactory.registerSingleton("textEncryptor", RegisterTextEncryptorListener.textEncryptor);
                }
            }
        }

    }

    private static final TextEncryptor NO_OP_TEXT_ENCRYPTOR = new TextEncryptor() {
        @Override
        public String encrypt(String text) {
            return text;
        }

        @Override
        public String decrypt(String encryptedText) {
            return encryptedText;
        }
    };

    private static TextEncryptor textEncryptor;

    public static final void setTextEncryptor(TextEncryptor textEncryptor) {
        RegisterTextEncryptorListener.textEncryptor = textEncryptor;
    }

    @Override
    public int getOrder() {
        return order;
    }


}
