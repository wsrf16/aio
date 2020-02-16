package com.aio.portable.swiss.suite.algorithm;

public abstract class HexConvert {
    private final static char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String toString1(byte[] bytes) {
        int j = bytes.length;
        char str[] = new char[j * 2];
        int index = 0;
        for (byte b : bytes) {
            str[index++] = HEX_DIGITS[b >>> 4 & 0xf];
            str[index++] = HEX_DIGITS[b & 0xf];
        }
        return new String(str);
    }


    public static String toString2(byte[] bytes) {
        // 一个byte为8位，可用两个十六进制位标识
        char[] buf = new char[bytes.length * 2];
        int a = 0;
        int index = 0;
        for (byte b : bytes) { // 使用除与取余进行转换
            if (b < 0) {
                a = 256 + b;
            } else {
                a = b;
            }

            buf[index++] = HEX_DIGITS[a / 16];
            buf[index++] = HEX_DIGITS[a % 16];
        }

        return new String(buf);
    }

    public static String toString3(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) { // 使用String的format方法进行转换
            buf.append(String.format("%02x", b & 0xff));
        }
        return buf.toString();
    }


    private final static int RADIX_16 = 16;

    public static byte[] toBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            // 16进制字符转换成int->位运算（取int(32位)低8位,即位与运算 &0xFF）->强转成byte
            bytes[i] = (byte) (0xFF & Integer.parseInt(hex.substring(i * 2, i * 2 + 2), RADIX_16));
        }
        return bytes;
    }
}
