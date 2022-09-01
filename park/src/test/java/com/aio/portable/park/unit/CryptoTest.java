package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.algorithm.crypto.aes.AESSugar;
import com.aio.portable.swiss.suite.algorithm.crypto.rsa.RSAKeyPair;
import com.aio.portable.swiss.suite.algorithm.crypto.rsa.RSASugar;
import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.security.PrivateKey;
import java.security.PublicKey;

@TestComponent
public class CryptoTest {
    static final String PK = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYeFrjw8C16HkkxxG3nG58EGHKo9CG6OXwQ+zvVkQNDipTdcuTodHvJqZ1oXriTlMmFWH/4XWNz7ejncZLyS2IB8HF0x6Phl+TquaxelRK9/d44aEUp9gYX7kCXwpU876K1xcBkFa/P+n4VZvnwCsxjqyThGCwC1rlOU0rPpuSvwIDAQAB";
    static final String SK = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANh4WuPDwLXoeSTHEbecbnwQYcqj0Ibo5fBD7O9WRA0OKlN1y5Oh0e8mpnWheuJOUyYVYf/hdY3Pt6OdxkvJLYgHwcXTHo+GX5Oq5rF6VEr393jhoRSn2BhfuQJfClTzvorXFwGQVr8/6fhVm+fAKzGOrJOEYLALWuU5TSs+m5K/AgMBAAECgYBbzC4/CHRgsAUvo3vP8XapBglydaWokHtyOFvx4xNYtfkdC4cZRDZSxIEywRfrgGuias70RBgl20B1EiNVYCr+DQI9v6hO7EC9qIOW5LV8OOCv5slOe1TP5fxCFZ71hvVLYzU1cCtfiZ+RGRtQFEm3X/UdcLcHS6HEZZP/P3iZgQJBAPQJCTwjBoQWYm0BLd2jNAMcRrgRQ/J1AKh3Od1SjLGAwLCpEeuxSpqQXE6sEg55aLfWRFJzVlSvGvjC5dwutb0CQQDjFVcG75xGFYSS7HJKyBKM1YO+CVsOX+Tq4CSaLGerhM4yzc0bR7ltNNAesrwNt5KDmXcU2h3/+6uHgA6sofwrAkBN0AfKzhxIx95whXy7fEucZkrCbHbu+5HPJd0kjirgen52liJptelk6X0VdNZ5GQtj+wVkCPTRIdiG05Z5o8c9AkBJ/MT7LIzFRPOKD2H5vZBVtKeU/mVdnJ7wbBFg7kDk4INyiB1g2xCuZTu+FMwyx/ID0Le84ohNxEkSAAZH9ws7AkEAsmJmpRC+/RYPTLq7t2DFJuPSkCDPG534OKo3fXrcHewvhvHygevCXqvRRvIdrX4PcEgUjZHSuBZ4i1Yi3GFm8A==";

    static final String PRI = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANh4WuPDwLXoeSTHEbecbnwQYcqj0Ibo5fBD7O9WRA0OKlN1y5Oh0e8mpnWheuJOUyYVYf/hdY3Pt6OdxkvJLYgHwcXTHo+GX5Oq5rF6VEr393jhoRSn2BhfuQJfClTzvorXFwGQVr8/6fhVm+fAKzGOrJOEYLALWuU5TSs+m5K/AgMBAAECgYBbzC4/CHRgsAUvo3vP8XapBglydaWokHtyOFvx4xNYtfkdC4cZRDZSxIEywRfrgGuias70RBgl20B1EiNVYCr+DQI9v6hO7EC9qIOW5LV8OOCv5slOe1TP5fxCFZ71hvVLYzU1cCtfiZ+RGRtQFEm3X/UdcLcHS6HEZZP/P3iZgQJBAPQJCTwjBoQWYm0BLd2jNAMcRrgRQ/J1AKh3Od1SjLGAwLCpEeuxSpqQXE6sEg55aLfWRFJzVlSvGvjC5dwutb0CQQDjFVcG75xGFYSS7HJKyBKM1YO+CVsOX+Tq4CSaLGerhM4yzc0bR7ltNNAesrwNt5KDmXcU2h3/+6uHgA6sofwrAkBN0AfKzhxIx95whXy7fEucZkrCbHbu+5HPJd0kjirgen52liJptelk6X0VdNZ5GQtj+wVkCPTRIdiG05Z5o8c9AkBJ/MT7LIzFRPOKD2H5vZBVtKeU/mVdnJ7wbBFg7kDk4INyiB1g2xCuZTu+FMwyx/ID0Le84ohNxEkSAAZH9ws7AkEAsmJmpRC+/RYPTLq7t2DFJuPSkCDPG534OKo3fXrcHewvhvHygevCXqvRRvIdrX4PcEgUjZHSuBZ4i1Yi3GFm8A==";
    static final String PUB = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYeFrjw8C16HkkxxG3nG58EGHKo9CG6OXwQ+zvVkQNDipTdcuTodHvJqZ1oXriTlMmFWH/4XWNz7ejncZLyS2IB8HF0x6Phl+TquaxelRK9/d44aEUp9gYX7kCXwpU876K1xcBkFa/P+n4VZvnwCsxjqyThGCwC1rlOU0rPpuSvwIDAQAB";

    @Test
    public void rsa() {
        RSAKeyPair rsaKeyPair = RSASugar.generateRSAKeyPair();
        PrivateKey privateKey = rsaKeyPair.getPrivateKey();
        PublicKey publicKey = rsaKeyPair.getPublicKey();
        String pri;
        String pub;

        pri = JDKBase64Convert.encodeToString(privateKey.getEncoded());
        pub = JDKBase64Convert.encodeToString(publicKey.getEncoded());

        pri = PRI;
        pub = PUB;

        {
            String cipher = RSASugar.encrypt("somesomething", pub);
            String plain = RSASugar.decrypt(cipher, pri);
            System.out.println();
        }
        {
//                String cipher = RSASugar.encrypt("somesomething", pri);
//                String plain = RSASugar.decrypt(cipher, pub);

            byte[] cipher1 = RSASugar.encrypt("somesomething".getBytes(), RSASugar.getPrivateKey(pri));
            byte[] plain1 = RSASugar.decrypt(cipher1, RSASugar.getPublicKey(pub));

            String cipher2 = JDKBase64Convert.encodeToString(RSASugar.encrypt("somesomething".getBytes(), RSASugar.getPrivateKey(pri)));
            String plain2 = new String(RSASugar.decrypt(JDKBase64Convert.decode(cipher2), RSASugar.getPublicKey(pub)));
            System.out.println();
        }

        String sign = RSASugar.sign("somesomething", pri);
        boolean verify = RSASugar.verify("somesomething", sign, pub);
    }

    @Test
    public void aes() {
        String secret = "kkkkkkkkkkkkkkkka";
        String cipher = AESSugar.encrypt("abcdefghijklmnopqrstuvwxyz0987654321", secret);
        String plain = AESSugar.decrypt(cipher, secret);
    }
}
