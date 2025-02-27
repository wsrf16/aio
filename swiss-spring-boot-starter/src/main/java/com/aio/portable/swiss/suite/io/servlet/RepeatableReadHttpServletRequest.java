package com.aio.portable.swiss.suite.io.servlet;

import com.aio.portable.swiss.suite.io.IOSugar;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Function;

// ContentCachingRequestWrapper
public class RepeatableReadHttpServletRequest extends HttpServletRequestWrapper implements HttpServletRequest {

//    private final HttpServletRequestImpl request;
    private byte[] body;
    private Function<byte[], byte[]> convert;


    public RepeatableReadHttpServletRequest(HttpServletRequest request, Function<byte[], byte[]> convert) {
        super(request);

        this.body = IOSugar.Streams.toByteArray(request);
        this.convert = convert;
    }

    public RepeatableReadHttpServletRequest(HttpServletRequest request) {
        this(request, b -> b);
    }

    public static final RepeatableReadHttpServletRequest repeatable(HttpServletRequest servletRequest) {
        return new RepeatableReadHttpServletRequest(servletRequest);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(convert.apply(body));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return inputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
        };
    }


//    public byte[] read(InputStream inputStream) {
//        byte[] bytes = new byte[1024];
//        int read = 0;
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        while (true) {
//            try {
//                if (!((read = inputStream.read(bytes)) != -1)) break;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            outputStream.write(bytes, 0, read);
//        }
//        return outputStream.toByteArray();
//    }
}
