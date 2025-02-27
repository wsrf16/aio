package com.aio.portable.swiss.suite.io;

import com.aio.portable.swiss.sugar.meta.ResourceSugar;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by York on 2017/11/28.
 */
public abstract class IOSugar {
    public static class Files {

        public static final File getDirectoryOfFile(File file) {
            File dir;
            if (file.isFile()) {
                dir = file.getParentFile();
                return dir;
            } else {
                throw new RuntimeException(file.getAbsolutePath() + " is not a file.");
            }
        }

        public static final File getDirectoryOfFile(String path) {
            File file = new File(path);
            return getDirectoryOfFile(file);
        }

//        public static final void createDirectoryIfNotExists(Path path) throws IOException {
//            if (Files.notExists(path))
//                Files.createDirectory(path);
//        }
//
//        public static final void createDirectoryIfNotExists(File directory) throws IOException {
//            Path path = directory.toPath();
//            if (Files.notExists(path))
//                Files.createDirectory(path);
//        }

        public static final String readFileForText(String path) {
            String text;
            try {
                File f = new File(path);
                if (f.isFile() && f.exists()) {
                    text = IOSugar.Streams.toString(f);
                } else if (ResourceSugar.existClassLoaderResource(path)) {
                    text = ResourceSugar.getResourceAsString(path);
                } else {
                    throw new FileNotFoundException(f.getAbsolutePath());
                }
                return text;
            } catch (Exception e) {
//                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public static final byte[] readFileForByte(String path) {
            try {
                FileInputStream fis = null;
                fis = new FileInputStream(path);

                byte[] bytes = new byte[(int) new File(path).length()];
                fis.read(bytes);
                return bytes;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public static final void writeFile(String path, String content) {
            try {
                File f = new File(path);
                if (!f.exists()) {
                    f.createNewFile();
                }
                OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
                BufferedWriter writer = new BufferedWriter(write);
                writer.write(content);
                writer.close();
            } catch (Exception e) {
//                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {

            }
        }

        public static final void writeFile(String path, byte[] bytes) {
            try {
                File file = new File(path);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes);
                fos.close();
            } catch (Exception e) {
//                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public static final boolean delete(String path) {
            File file = new File(path);
            return delete(file);
        }

        public static final boolean delete(File path) {
            boolean hasDeleted = false;
            if (path.exists()) {
                if (path.isDirectory()) {
                    File files[] = path.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        delete(files[i]);
                    }
                }
                hasDeleted = true;
                path.delete();
            }
            return hasDeleted;
        }

        public static final File createParentDirectories(File path) {
            File dir = path.getParentFile();
            return createDirectories(dir);
        }

        public static final File createDirectories(File dir) {
            if (null != dir && !dir.exists()) {
                dir.mkdirs();
            }
            return dir;
        }

        public static final File createParentDirectories(String path) {
            File dir = new File(path);
            return createParentDirectories(dir);
        }

        public static final File createDirectories(String path) {
            File dir = new File(path);
            return createDirectories(dir);
        }
    }

    public static class Closeables {
        public static void close(URLConnection conn) {
            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).disconnect();
            }

        }

        public static void closeQuietly(Reader input) {
            closeQuietly((Closeable)input);
        }

        public static void closeQuietly(Writer output) {
            closeQuietly((Closeable)output);
        }

        public static void closeQuietly(InputStream input) {
            closeQuietly((Closeable)input);
        }

        public static void closeQuietly(OutputStream output) {
            closeQuietly((Closeable)output);
        }

        public static void closeQuietly(Closeable closeable) {
            try {
                if (closeable != null) {
                    closeable.close();
                }
            } catch (IOException var2) {
            }

        }

        public static void closeQuietly(Socket sock) {
            if (sock != null) {
                try {
                    sock.close();
                } catch (IOException var2) {
                }
            }

        }

        public static void closeQuietly(Selector selector) {
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException var2) {
                }
            }

        }

        public static void closeQuietly(ServerSocket sock) {
            if (sock != null) {
                try {
                    sock.close();
                } catch (IOException var2) {
                }
            }

        }
    }

    public static class Streams {
        public static final String toString(String path) {
            try (FileInputStream inputStream = new FileInputStream(path)) {
                return toString(inputStream, StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static final String toString(String path, Charset charset) {
            try (FileInputStream inputStream = new FileInputStream(path)) {
                return toString(inputStream, charset);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static final String toString(File file) {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                return toString(inputStream, StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static final String toString(File file, Charset charset) {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                return toString(inputStream, charset);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static final String toString(InputStream inputStream) {
            return toString(inputStream, StandardCharsets.UTF_8);
        }

        public static final String toString(Reader reader) {
            try {
                return FileCopyUtils.copyToString(reader);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static final String toString(InputStream inputStream, Charset charset) {
//            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
//                byte[] buffer = new byte[1024];
//                int length;
//                try {
//                    while ((length = inputStream.read(buffer)) != -1) {
//                        outputStream.write(buffer, 0, length);
//                    }
//                    return outputStream.toString(charset.displayName());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
            try (Reader reader = new InputStreamReader(inputStream, charset)) {
                return FileCopyUtils.copyToString(reader);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }


//        private static final FileInputStream newFileInputStream(File file) {
//            try(FileInputStream fileInputStream = new FileInputStream(file)) {
//                return fileInputStream;
//            } catch (IOException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//        }

        public static byte[] toByteArray(String filePath) {
            return toByteArray(new File(filePath));
        }

        public static byte[] toByteArray(File file) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return toByteArray(fileInputStream);
        }

        public static byte[] toByteArray(InputStream input) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            copy(input, output);
            return output.toByteArray();
        }

        public static byte[] toByteArray(HttpServletRequest request) {
            try {
                return Streams.toByteArray(request.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static byte[] toByteArray(InputStream input, int size) {
            if (size < 0) {
                throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
            }

            if (size == 0) {
                return new byte[0];
            }

            byte[] data = new byte[size];
            int offset = 0;
            int readed;

            try {
                while (offset < size && (readed = input.read(data, offset, size - offset)) != EOF) {
                    offset += readed;
                }

                if (offset != size) {
                    throw new RuntimeException("Unexpected readed size. current: " + offset + ", excepted: " + size);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return data;
        }

        public static byte[] toByteArray(URLConnection urlConn) {
            try {
                InputStream inputStream = urlConn.getInputStream();
                try {
                    return IOSugar.Streams.toByteArray(inputStream);
                } finally {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static int copy(InputStream input, OutputStream output) {
            long count = copyLarge(input, output);
            if (count > Integer.MAX_VALUE) {
                return -1;
            }
            return (int) count;
        }

        private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
        public static long copyLarge(InputStream input, OutputStream output) {
            return copyLarge(input, output, new byte[DEFAULT_BUFFER_SIZE]);
        }

        private static final int EOF = -1;
        public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) {
            long count = 0;
            int n = 0;
            try {
                while (EOF != (n = input.read(buffer))) {
                    output.write(buffer, 0, n);
                    count += n;
                }
            } catch (IOException e) {
//                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return count;
        }

        public static ClonedStream clone(InputStream inputStream) {
            byte[] buffer = IOSugar.Streams.toByteArray(inputStream);
            ByteArrayInputStream clonedInputStream = new ByteArrayInputStream(buffer);
            return new ClonedStream(clonedInputStream, buffer);
        }

        public static class ClonedStream {
            private InputStream inputStream;
            private byte[] bytes;

            public InputStream getInputStream() {
                return inputStream;
            }

            public void setInputStream(InputStream inputStream) {
                this.inputStream = inputStream;
            }

            public byte[] getBytes() {
                return bytes;
            }

            public void setBytes(byte[] bytes) {
                this.bytes = bytes;
            }

            public ClonedStream(InputStream inputStream, byte[] bytes) {
                this.inputStream = inputStream;
                this.bytes = bytes;
            }
        }



    }
}
