package com.aio.portable.swiss.spring.web;

import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import com.aio.portable.swiss.suite.algorithm.identity.SerialNumberWorker;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class Base64MultipartFile implements MultipartFile {
    private SerialNumberWorker.SerialNumberBuilder builder = SerialNumberWorker.builder();

    private final byte[] bytes;

    private final String header;

    private Base64MultipartFile(String header, byte[] bytes) {
        this.header = header;
        this.bytes = bytes;
    }

    public static Base64MultipartFile toMultipartFile(String header, byte[] image) {
        return new Base64MultipartFile(header, image);
    }

    /**
     * toMultipartFile
     * @param base64 eg. "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAA4KCwwLCQ4MCwwQDw4RFSMXFRMTFSsfIRojMy02NTItMTA4P1FFODxNPTAxRmBHTVRWW1xbN0RjamNYalFZW1f/2wBDAQ8QEBUSFSkXFylXOjE6V1dXV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1f/wAARCAK9ArwDASIAAhEBAxEB/8QAHAABAAIDAQEBAAAAAAAAAAAAAAUGAQQHAwII/8QAORABAAICAgAFAwEGBAUEAwAAAAECAwQFEQYhMUFREhNhcQcUIoGRoTJCscEVIyQz0RYlNVJicnP/xAAZAQEAAwEBAAAAAAAAAAAAAAAAAQIDBAX/xAAiEQEBAAICAwEAAgMAAAAAAAAAAQIRAyEEEjFBFFEiI2H/2gAMAwEAAhEDEQA/AOkgAAAAAAAAAAAAAAAAAAAAAAAAAAAA..."
     * @return
     */
    public static Base64MultipartFile toMultipartFile(String base64) {
        String[] charArray = base64.split(",");

        byte[] bytes = JDKBase64Convert.decode(charArray[1]);
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] < 0) {
                bytes[i] += 256;
            }
        }
        return Base64MultipartFile.toMultipartFile(charArray[0], bytes);
    }

    @Override
    public String getName() {
        return  builder.build() + "." + header.split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        return builder.build() + "." + header.split(";")[0].split("/")[1];
    }

    @Override
    public String getContentType() {
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return bytes == null || bytes.length == 0;
    }

    @Override
    public long getSize() {
        return bytes.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return bytes;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public void transferTo(File file) {
        try {
            new FileOutputStream(file).write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
