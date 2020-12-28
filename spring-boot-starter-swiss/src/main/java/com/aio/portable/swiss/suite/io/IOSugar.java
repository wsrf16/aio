package com.aio.portable.swiss.suite.io;

import java.io.*;
import java.net.*;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by York on 2017/11/28.
 */
public abstract class IOSugar {
    public static class Files {

        public final static File getDirectoryOfFile(File file) {
            File dir;
            if (file.isFile()) {
                dir = file.getParentFile();
                return dir;
            } else {
                throw new RuntimeException(file.getAbsolutePath() + " is not a file.");
            }
        }

        public final static File getDirectoryOfFile(String path) {
            File file = new File(path);
            return getDirectoryOfFile(file);
        }

//        public final static void createDirectoryIfNotExists(Path path) throws IOException {
//            if (Files.notExists(path))
//                Files.createDirectory(path);
//        }
//
//        public final static void createDirectoryIfNotExists(File directory) throws IOException {
//            Path path = directory.toPath();
//            if (Files.notExists(path))
//                Files.createDirectory(path);
//        }

        public final static String readFileForText(String path) {
            StringBuffer sb = null;
            try {
                File f = new File(path);
                if (f.isFile() && f.exists()) {
                    sb = new StringBuffer();
                    InputStreamReader read = new InputStreamReader(new FileInputStream(f), "UTF-8");
                    BufferedReader reader = new BufferedReader(read);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    read.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        public final static byte[] readFileForByte(String path) {
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

        public final static void writeFile(String path, String content) {
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
                e.printStackTrace();
            } finally {

            }
        }

        public final static void writeFile(String path, byte[] bytes) {
            try {
                File file = new File(path);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public final static boolean delete(String path) {
            File file = new File(path);
            return delete(file);
        }

        public final static boolean delete(File path) {
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

        public final static File createParentDirectories(File path) {
            File dir = path.getParentFile();
            return createDirectories(dir);
        }

        public final static File createDirectories(File dir) {
            if (null != dir && !dir.exists()) {
                dir.mkdirs();
            }
            return dir;
        }

        public final static File createParentDirectories(String path) {
            File dir = new File(path);
            return createParentDirectories(dir);
        }

        public final static File createDirectories(String path) {
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


//        private final static FileInputStream newFileInputStream(File file) {
//            try(FileInputStream fileInputStream = new FileInputStream(file)) {
//                return fileInputStream;
//            } catch (IOException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//        }

        public static byte[] toByteArray(String filePath) throws IOException {
            File file = new File(filePath);
            return toByteArray(file);
        }

        public static byte[] toByteArray(File file) throws IOException {
            FileInputStream fileInputStream = new FileInputStream(file);
            return toByteArray(fileInputStream);
        }

        public static byte[] toByteArray(InputStream input) throws IOException {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            copy(input, output);
            return output.toByteArray();
        }

        public static byte[] toByteArray(InputStream input, int size) throws IOException {
            if (size < 0) {
                throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
            } else if (size == 0) {
                return new byte[0];
            } else {
                byte[] data = new byte[size];

                int offset;
                int readed;
                for(offset = 0; offset < size && (readed = input.read(data, offset, size - offset)) != -1; offset += readed) {
                }

                if (offset != size) {
                    throw new IOException("Unexpected readed size. current: " + offset + ", excepted: " + size);
                } else {
                    return data;
                }
            }
        }

        public static byte[] toByteArray(URI uri) throws IOException {
            return toByteArray(uri.toURL());
        }

        public static byte[] toByteArray(URL url) throws IOException {
            URLConnection conn = url.openConnection();

            byte[] var2;
            try {
                var2 = toByteArray(conn);
            } finally {
                Closeables.close(conn);
            }

            return var2;
        }

        public static byte[] toByteArray(URLConnection urlConn) throws IOException {
            InputStream inputStream = urlConn.getInputStream();

            byte[] var2;
            try {
                var2 = toByteArray(inputStream);
            } finally {
                inputStream.close();
            }

            return var2;
        }

        public static int copy(InputStream input, OutputStream output) throws IOException {
            long count = copyLarge(input, output);
            return count > 2147483647L ? -1 : (int)count;
        }

        public static long copyLarge(InputStream input, OutputStream output) throws IOException {
            return copyLarge(input, output, new byte[4096]);
        }

        public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
            long count = 0L;

            int n;
            for(boolean var5 = false; -1 != (n = input.read(buffer)); count += (long)n) {
                output.write(buffer, 0, n);
            }

            return count;
        }
    }
}
