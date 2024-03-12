package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.algorithm.crypto.passwordencoder.PasswordEncoderFactories;
import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import com.aio.portable.swiss.suite.algorithm.encode.SpringBase64Convert;
import com.aio.portable.swiss.suite.algorithm.geo.GeoHash;
import com.aio.portable.swiss.suite.algorithm.geo.GeoPoint;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestComponent
public class Base64Test {
    @Test
    public void foobar() {
//        // 40.222012, 116.248283
////        GeoHash geohash = new GeoHash();
//        String s = geohash.encode(39.9257460000, 116.5998310000);
////        System.out.println(s);
//        geohash.getArroundGeoHash(39.9257460000, 116.5998310000);
//        double[] geo = geohash.decode(s);
////        System.out.println(geo[0]+" "+geo[1]);

//        String encode = new GeoHashHelper().encode(39.97696,116.3764874, 20);

        String hash = GeoHash.encode(39.97696,116.3764874, 7);
        GeoPoint wx4g2jw = GeoHash.decode("wx4g2jw");
        GeoPoint wx4g2jw1 = GeoHash.decodeToGeoHash("wx4g2jw").getBoundingBoxCenterPoint();
    }

    @Test
    public void foobar1() {

    }
}
