package com.aio.portable.swiss.suite.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class IOStreamSugar {

//    public final static String toString(InputStream inputStream, Charset charset) throws IOException {
//
//        String result = CharStreams.toString(new InputStreamReader(inputStream, charset));
//        return result;
//    }

    public final static String toString(InputStream inputStream) {
        return toString(inputStream, StandardCharsets.UTF_8);
    }

    public final static String toString(InputStream inputStream, Charset charset) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            return outputStream.toString(charset.displayName());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
