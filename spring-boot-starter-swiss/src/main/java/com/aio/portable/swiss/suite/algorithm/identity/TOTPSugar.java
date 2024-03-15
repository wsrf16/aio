package com.aio.portable.swiss.suite.algorithm.identity;

import com.aio.portable.swiss.sugar.type.DateTimeSugar;
import com.aio.portable.swiss.sugar.type.NumberSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.algorithm.encode.Base32Convert;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;

// Time-based One-time Password RFC6238
public abstract class TOTPSugar {
//    static {
//        hash = HashConvert.SHA1::encodeToHex;
//    }
//
//    private static Function<String, String> hash;
//
//    public static Function<String, String> getHash() {
//        return hash;
//    }
//
//    public static void setHash(Function<String, String> hash) {
//        TOTPSugar.hash = hash;
//    }
//
//    public static final String build(String secret) {
//        return build(secret, 30);
//    }
//
//    public static final String build(String secret, int period) {
//        return build(secret, period, 6);
//    }
//
//    private static final String build(String secret, int period, int digits) {
//        Pattern pattern = Pattern.compile("\\d");
//        String key = secret;
//
//        String TC = String.valueOf((int) Math.floor(DateTimeSugar.UnixTime.nowUnixSeconds() / period));
//        String TOTP = hash(key + TC);
//        String result = afterHash(TOTP, digits);
//        return result;
//    }
//
//    private static final String hash(String text) {
////        return JDKBase64Convert.encodeToString(text);
//        return hash.apply(text);
//    }
//
//    private static final String afterHash(String TOTP, int digits) {
//        String result = TOTP.replaceAll("\\D", "");
//        if (result.length() > digits)
//            result = result.substring(result.length() - digits);
//        else {
//            throw new RuntimeException("digit is too long.");
//        }
//        return result;
//    }

    public static String generate(String secret) {
        return generateByPeriod(secret, 30, 6);
    }

    public static String generateByTime(String secret, long time, int digits) {
        byte[] secretBytes = Base32Convert.decode(secret);
        return TOTP.generateTOTP(secretBytes, time, digits);
    }

    public static String generateByPeriod(String secret, int period, int digits) {
        byte[] secretBytes = Base32Convert.decode(secret);
        long time = DateTimeSugar.UnixTime.nowUnixSeconds() / period;
        return TOTP.generateTOTP(secretBytes, time, digits);
    }

    static class TOTP {
        private static final int[] DIGITS_POWER
                // 0 1  2   3    4     5      6       7        8
                = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};

        /**
         * This method generates a TOTP value for the given
         * set of parameters.
         *
         * @param key:    the shared secret, HEX encoded
         * @param time:   a value that reflects a time
         * @param digits: number of digits to return
         * @return: a numeric String in base 10 that include truncationDigits digits
         */
        private static String generateTOTP(byte[] key, long time, int digits) {
            return generateTOTP(key, time, digits, "HmacSHA1");
        }

        /**
         * This method generates a TOTP value for the given
         * set of parameters.
         *
         * @param key:    the shared secret, HEX encoded
         * @param time:   a value that reflects a time
         * @param digits: number of digits to return
         * @return: a numeric String in base 10 that includes truncationDigits digits
         */
        private static String generateTOTP256(byte[] key, long time, int digits) {
            return generateTOTP(key, time, digits, "HmacSHA256");
        }

        /**
         * This method generates a TOTP value for the given
         * set of parameters.
         *
         * @param key:    the shared secret, HEX encoded
         * @param time:   a value that reflects a time
         * @param digits: number of digits to return
         * @return: a numeric String in base 10 that includes truncationDigits digits
         */
        private static String generateTOTP512(byte[] key, long time, int digits) {
            return generateTOTP(key, time, digits, "HmacSHA512");
        }


        /**
         * This method generates a TOTP value for the given
         * set of parameters.
         *
         * @param key:    the shared secret, HEX encoded
         * @param time:   a value that reflects a time
         * @param digits: number of digits to return
         * @param crypto: the crypto function to use
         * @return: a numeric String in base 10 that includes truncationDigits digits
         */
        private static String generateTOTP(byte[] key, long time, int digits, String crypto) {
            byte[] msg = NumberSugar.convertLongToBytes(time);
            byte[] hash = hmac_sha(crypto, key, msg);

            // put selected bytes into result int
            int offset = hash[hash.length - 1] & 0xf;

            int binary = ((hash[offset] & 0x7f) << 24) |
                    ((hash[offset + 1] & 0xff) << 16) |
                    ((hash[offset + 2] & 0xff) << 8) |
                    (hash[offset + 3] & 0xff);

            int otp = binary % DIGITS_POWER[digits];

            return StringSugar.leftPad(Integer.toString(otp), digits, "0");


//            StringBuilder result = new StringBuilder(Integer.toString(otp));
//            while (result.length() < digits) {
//                result.insert(0, "0");
//            }
//            return result.toString();
        }

        private TOTP() {
        }

        /**
         * This method uses the JCE to provide the crypto algorithm.
         * HMAC computes a Hashed Message Authentication Code with the
         * crypto hash algorithm as a parameter.
         *
         * @param crypto:   the crypto algorithm (HmacSHA1, HmacSHA256,
         *                  HmacSHA512)
         * @param keyBytes: the bytes to use for the HMAC key
         * @param text:     the message or text to be authenticated
         */

        private static byte[] hmac_sha(String crypto, byte[] keyBytes, byte[] text) {
            try {
                Mac hmac;
                hmac = Mac.getInstance(crypto);
                SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
                hmac.init(macKey);
                return hmac.doFinal(text);
            } catch (GeneralSecurityException gse) {
                throw new UndeclaredThrowableException(gse);
            }
        }
    }
}
