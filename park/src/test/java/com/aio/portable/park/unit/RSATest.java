package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.algorithm.crypto.rsa.RSAKeyPair;
import com.aio.portable.swiss.suite.algorithm.crypto.rsa.RSASugar;
import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.security.PrivateKey;
import java.security.PublicKey;

@TestComponent
public class RSATest {
    final static String PRI = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANh4WuPDwLXoeSTHEbecbnwQYcqj0Ibo5fBD7O9WRA0OKlN1y5Oh0e8mpnWheuJOUyYVYf/hdY3Pt6OdxkvJLYgHwcXTHo+GX5Oq5rF6VEr393jhoRSn2BhfuQJfClTzvorXFwGQVr8/6fhVm+fAKzGOrJOEYLALWuU5TSs+m5K/AgMBAAECgYBbzC4/CHRgsAUvo3vP8XapBglydaWokHtyOFvx4xNYtfkdC4cZRDZSxIEywRfrgGuias70RBgl20B1EiNVYCr+DQI9v6hO7EC9qIOW5LV8OOCv5slOe1TP5fxCFZ71hvVLYzU1cCtfiZ+RGRtQFEm3X/UdcLcHS6HEZZP/P3iZgQJBAPQJCTwjBoQWYm0BLd2jNAMcRrgRQ/J1AKh3Od1SjLGAwLCpEeuxSpqQXE6sEg55aLfWRFJzVlSvGvjC5dwutb0CQQDjFVcG75xGFYSS7HJKyBKM1YO+CVsOX+Tq4CSaLGerhM4yzc0bR7ltNNAesrwNt5KDmXcU2h3/+6uHgA6sofwrAkBN0AfKzhxIx95whXy7fEucZkrCbHbu+5HPJd0kjirgen52liJptelk6X0VdNZ5GQtj+wVkCPTRIdiG05Z5o8c9AkBJ/MT7LIzFRPOKD2H5vZBVtKeU/mVdnJ7wbBFg7kDk4INyiB1g2xCuZTu+FMwyx/ID0Le84ohNxEkSAAZH9ws7AkEAsmJmpRC+/RYPTLq7t2DFJuPSkCDPG534OKo3fXrcHewvhvHygevCXqvRRvIdrX4PcEgUjZHSuBZ4i1Yi3GFm8A==";
    final static String PUB = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYeFrjw8C16HkkxxG3nG58EGHKo9CG6OXwQ+zvVkQNDipTdcuTodHvJqZ1oXriTlMmFWH/4XWNz7ejncZLyS2IB8HF0x6Phl+TquaxelRK9/d44aEUp9gYX7kCXwpU876K1xcBkFa/P+n4VZvnwCsxjqyThGCwC1rlOU0rPpuSvwIDAQAB";

    @Test
    public void foobar() {
        final RSAKeyPair rsaKeyPair = RSASugar.generateRSAKeyPair();
        final PrivateKey privateKey = rsaKeyPair.getPrivateKey();
        final PublicKey publicKey = rsaKeyPair.getPublicKey();
        String pri;
        String pub;

        pri = JDKBase64Convert.encodeToString(privateKey.getEncoded());
        pub = JDKBase64Convert.encodeToString(publicKey.getEncoded());

        pri = PRI;
        pub = PUB;

        final String aaaaa = RSASugar.encrypt("somesomething", pub);
        final String bbbbb = RSASugar.decrypt(aaaaa, pri);

        final String aaaaaa = RSASugar.sign("somesomething", pri);
        final boolean bbbbbb = RSASugar.verify("aaaaaa", aaaaaa, pub);


    }
}
