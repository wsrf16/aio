package com.aio.portable.park.unit;

import com.aio.portable.swiss.spring.factories.listener.RegisterTextEncryptorListener;
import com.aio.portable.swiss.suite.algorithm.crypto.aes.AESSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@TestComponent
public class RegisterTextEncryptorListenerTest {
    @Test
    public void todo() {
        RegisterTextEncryptorListener.setTextEncryptor(new TextEncryptor() {
            @Override
            public String encrypt(String text) {
                return AESSugar.encrypt(text, "pwd");
            }

            @Override
            public String decrypt(String encryptedText) {
                return AESSugar.decrypt(encryptedText, "pwd");
            }
        });
    }
}
