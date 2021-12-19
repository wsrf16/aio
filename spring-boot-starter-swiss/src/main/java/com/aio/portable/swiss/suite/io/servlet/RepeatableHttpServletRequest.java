package com.aio.portable.swiss.suite.io.servlet;

import com.aio.portable.swiss.suite.io.IOSugar;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Function;

public class RepeatableHttpServletRequest extends HttpServletRequestWrapper {

//    private final HttpServletRequestImpl request;
    private byte[] body;
    private Function<byte[], byte[]> convert;


    public RepeatableHttpServletRequest(HttpServletRequest request, Function<byte[], byte[]> convert) {
        super(request);

        byte[] body = readInputStream(request);
        if (body != null) {
            this.body = body;
        }
        this.convert = convert;
    }

    public RepeatableHttpServletRequest(HttpServletRequest request) {
        super(request);

        byte[] body = readInputStream(request);
        if (body != null) {
            this.body = body;
        }
        this.convert = _body -> _body;
    }

    public final static RepeatableHttpServletRequest repeatable(HttpServletRequest servletRequest) {
        return new RepeatableHttpServletRequest(servletRequest);
    }

    private byte[] readInputStream(HttpServletRequest request) {
        try {
            byte[] body = IOSugar.Streams.toByteArray(request.getInputStream());
            return body;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

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
