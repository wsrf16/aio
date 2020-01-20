package com.aio.portable.swiss.sugar.algorithm.cipher;

public abstract class HexConvert {
    private final static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getHex(byte[] bytes) {
        int j = bytes.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = bytes[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }


    private final static int RADIX_16 = 16;

    public static byte[] getBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            // 16进制字符转换成int->位运算（取int(32位)低8位,即位与运算 &0xFF）->强转成byte
            bytes[i] = (byte) (0xFF & Integer.parseInt(hex.substring(i * 2, i * 2 + 2), RADIX_16));
        }
        return bytes;
    }
}
