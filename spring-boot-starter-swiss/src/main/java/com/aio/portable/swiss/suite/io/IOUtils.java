//package com.aio.portable.swiss.suite.io;
//
//import java.io.*;
//import java.net.*;
//import java.nio.channels.Selector;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//
//public class IOUtils {
//    private static final int EOF = -1;
//    public static final char DIR_SEPARATOR_UNIX = '/';
//    public static final char DIR_SEPARATOR_WINDOWS = '\\';
//    public static final char DIR_SEPARATOR;
//    public static final String LINE_SEPARATOR_UNIX = "\n";
//    public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
////    public static final String LINE_SEPARATOR;
//    private static final int DEFAULT_BUFFER_SIZE = 4096;
//    private static final int SKIP_BUFFER_SIZE = 2048;
//    private static char[] SKIP_CHAR_BUFFER;
//    private static byte[] SKIP_BYTE_BUFFER;
//
//    public IOUtils() {
//    }
//
//
//
//    public static BufferedReader toBufferedReader(Reader reader) {
//        return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);
//    }
//
//
//
//    public static String toString(URI uri, String encoding) throws IOException {
//        return toString(uri, encoding);
//    }
//
//    /** @deprecated */
//    @Deprecated
//    public static String toString(byte[] input) throws IOException {
//        return new String(input);
//    }
//
//    public static List<String> readLines(InputStream input) throws IOException {
//        return readLines(input, Charset.defaultCharset());
//    }
//
//    public static List<String> readLines(InputStream input, Charset encoding) throws IOException {
//        InputStreamReader reader = new InputStreamReader(input, encoding);
//        return readLines((Reader)reader);
//    }
//
//    public static List<String> readLines(Reader input) throws IOException {
//        BufferedReader reader = toBufferedReader(input);
//        List<String> list = new ArrayList();
//
//        for(String line = reader.readLine(); line != null; line = reader.readLine()) {
//            list.add(line);
//        }
//
//        return list;
//    }
//
//    public static void write(byte[] data, OutputStream output) throws IOException {
//        if (data != null) {
//            output.write(data);
//        }
//
//    }
//
//    public static void write(byte[] data, Writer output) throws IOException {
//        write(data, output, Charset.defaultCharset());
//    }
//
//    public static void write(byte[] data, Writer output, Charset encoding) throws IOException {
//        if (data != null) {
//            output.write(new String(data, encoding));
//        }
//
//    }
//
//    public static void write(char[] data, Writer output) throws IOException {
//        if (data != null) {
//            output.write(data);
//        }
//
//    }
//
//    public static void write(char[] data, OutputStream output) throws IOException {
//        write(data, output, Charset.defaultCharset());
//    }
//
//    public static void write(char[] data, OutputStream output, Charset encoding) throws IOException {
//        if (data != null) {
//            output.write((new String(data)).getBytes(encoding));
//        }
//
//    }
//
//    public static void write(CharSequence data, Writer output) throws IOException {
//        if (data != null) {
//            write(data.toString(), output);
//        }
//
//    }
//
//    public static void write(CharSequence data, OutputStream output) throws IOException {
//        write(data, output, Charset.defaultCharset());
//    }
//
//    public static void write(CharSequence data, OutputStream output, Charset encoding) throws IOException {
//        if (data != null) {
//            write(data.toString(), output, encoding);
//        }
//
//    }
//
//    public static void write(String data, Writer output) throws IOException {
//        if (data != null) {
//            output.write(data);
//        }
//
//    }
//
//    public static void write(String data, OutputStream output) throws IOException {
//        write(data, output, Charset.defaultCharset());
//    }
//
//    public static void write(String data, OutputStream output, Charset encoding) throws IOException {
//        if (data != null) {
//            output.write(data.getBytes(encoding));
//        }
//
//    }
//
//    /** @deprecated */
//    @Deprecated
//    public static void write(StringBuffer data, Writer output) throws IOException {
//        if (data != null) {
//            output.write(data.toString());
//        }
//
//    }
//
//    public static long copyLarge(InputStream input, OutputStream output, long inputOffset, long length) throws IOException {
//        return copyLarge(input, output, inputOffset, length, new byte[4096]);
//    }
//
//    public static long copyLarge(InputStream input, OutputStream output, long inputOffset, long length, byte[] buffer) throws IOException {
//        if (inputOffset > 0L) {
//            skipFully(input, inputOffset);
//        }
//
//        if (length == 0L) {
//            return 0L;
//        } else {
//            int bufferLength = buffer.length;
//            int bytesToRead = bufferLength;
//            if (length > 0L && length < (long)bufferLength) {
//                bytesToRead = (int)length;
//            }
//
//            long totalRead = 0L;
//
//            int read;
//            while(bytesToRead > 0 && -1 != (read = input.read(buffer, 0, bytesToRead))) {
//                output.write(buffer, 0, read);
//                totalRead += (long)read;
//                if (length > 0L) {
//                    bytesToRead = (int)Math.min(length - totalRead, (long)bufferLength);
//                }
//            }
//
//            return totalRead;
//        }
//    }
//
//    public static void copy(InputStream input, Writer output) throws IOException {
//        copy(input, output, Charset.defaultCharset());
//    }
//
//    public static void copy(InputStream input, Writer output, Charset encoding) throws IOException {
//        InputStreamReader in = new InputStreamReader(input, encoding);
//        copy((Reader)in, (Writer)output);
//    }
//
//    public static int copy(Reader input, Writer output) throws IOException {
//        long count = copyLarge(input, output);
//        return count > 2147483647L ? -1 : (int)count;
//    }
//
//    public static long copyLarge(Reader input, Writer output) throws IOException {
//        return copyLarge(input, output, new char[4096]);
//    }
//
//    public static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
//        long count = 0L;
//
//        int n;
//        for(boolean var5 = false; -1 != (n = input.read(buffer)); count += (long)n) {
//            output.write(buffer, 0, n);
//        }
//
//        return count;
//    }
//
//    public static long copyLarge(Reader input, Writer output, long inputOffset, long length) throws IOException {
//        return copyLarge(input, output, inputOffset, length, new char[4096]);
//    }
//
//    public static long copyLarge(Reader input, Writer output, long inputOffset, long length, char[] buffer) throws IOException {
//        if (inputOffset > 0L) {
//            skipFully(input, inputOffset);
//        }
//
//        if (length == 0L) {
//            return 0L;
//        } else {
//            int bytesToRead = buffer.length;
//            if (length > 0L && length < (long)buffer.length) {
//                bytesToRead = (int)length;
//            }
//
//            long totalRead = 0L;
//
//            int read;
//            while(bytesToRead > 0 && -1 != (read = input.read(buffer, 0, bytesToRead))) {
//                output.write(buffer, 0, read);
//                totalRead += (long)read;
//                if (length > 0L) {
//                    bytesToRead = (int)Math.min(length - totalRead, (long)buffer.length);
//                }
//            }
//
//            return totalRead;
//        }
//    }
//
//    public static void copy(Reader input, OutputStream output) throws IOException {
//        copy(input, output, Charset.defaultCharset());
//    }
//
//    public static void copy(Reader input, OutputStream output, Charset encoding) throws IOException {
//        OutputStreamWriter out = new OutputStreamWriter(output, encoding);
//        copy((Reader)input, (Writer)out);
//        out.flush();
//    }
//
//    public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
//        if (!(input1 instanceof BufferedInputStream)) {
//            input1 = new BufferedInputStream((InputStream)input1);
//        }
//
//        if (!(input2 instanceof BufferedInputStream)) {
//            input2 = new BufferedInputStream((InputStream)input2);
//        }
//
//        int ch2;
//        for(int ch = ((InputStream)input1).read(); -1 != ch; ch = ((InputStream)input1).read()) {
//            ch2 = ((InputStream)input2).read();
//            if (ch != ch2) {
//                return false;
//            }
//        }
//
//        ch2 = ((InputStream)input2).read();
//        return ch2 == -1;
//    }
//
//    public static long skip(InputStream input, long toSkip) throws IOException {
//        if (toSkip < 0L) {
//            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
//        } else {
//            if (SKIP_BYTE_BUFFER == null) {
//                SKIP_BYTE_BUFFER = new byte[2048];
//            }
//
//            long remain;
//            long n;
//            for(remain = toSkip; remain > 0L; remain -= n) {
//                n = (long)input.read(SKIP_BYTE_BUFFER, 0, (int)Math.min(remain, 2048L));
//                if (n < 0L) {
//                    break;
//                }
//            }
//
//            return toSkip - remain;
//        }
//    }
//
//    public static long skip(Reader input, long toSkip) throws IOException {
//        if (toSkip < 0L) {
//            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
//        } else {
//            if (SKIP_CHAR_BUFFER == null) {
//                SKIP_CHAR_BUFFER = new char[2048];
//            }
//
//            long remain;
//            long n;
//            for(remain = toSkip; remain > 0L; remain -= n) {
//                n = (long)input.read(SKIP_CHAR_BUFFER, 0, (int)Math.min(remain, 2048L));
//                if (n < 0L) {
//                    break;
//                }
//            }
//
//            return toSkip - remain;
//        }
//    }
//
//    public static void skipFully(InputStream input, long toSkip) throws IOException {
//        if (toSkip < 0L) {
//            throw new IllegalArgumentException("Bytes to skip must not be negative: " + toSkip);
//        } else {
//            long skipped = skip(input, toSkip);
//            if (skipped != toSkip) {
//                throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
//            }
//        }
//    }
//
//    public static void skipFully(Reader input, long toSkip) throws IOException {
//        long skipped = skip(input, toSkip);
//        if (skipped != toSkip) {
//            throw new EOFException("Chars to skip: " + toSkip + " actual: " + skipped);
//        }
//    }
//
//    public static int read(Reader input, char[] buffer, int offset, int length) throws IOException {
//        if (length < 0) {
//            throw new IllegalArgumentException("Length must not be negative: " + length);
//        } else {
//            int remaining;
//            int count;
//            for(remaining = length; remaining > 0; remaining -= count) {
//                int location = length - remaining;
//                count = input.read(buffer, offset + location, remaining);
//                if (-1 == count) {
//                    break;
//                }
//            }
//
//            return length - remaining;
//        }
//    }
//
//    public static int read(Reader input, char[] buffer) throws IOException {
//        return read((Reader)input, (char[])buffer, 0, buffer.length);
//    }
//
//    public static int read(InputStream input, byte[] buffer, int offset, int length) throws IOException {
//        if (length < 0) {
//            throw new IllegalArgumentException("Length must not be negative: " + length);
//        } else {
//            int remaining;
//            int count;
//            for(remaining = length; remaining > 0; remaining -= count) {
//                int location = length - remaining;
//                count = input.read(buffer, offset + location, remaining);
//                if (-1 == count) {
//                    break;
//                }
//            }
//
//            return length - remaining;
//        }
//    }
//
//    public static int read(InputStream input, byte[] buffer) throws IOException {
//        return read((InputStream)input, (byte[])buffer, 0, buffer.length);
//    }
//
//    public static void readFully(Reader input, char[] buffer, int offset, int length) throws IOException {
//        int actual = read(input, buffer, offset, length);
//        if (actual != length) {
//            throw new EOFException("Length to read: " + length + " actual: " + actual);
//        }
//    }
//
//    public static void readFully(Reader input, char[] buffer) throws IOException {
//        readFully((Reader)input, (char[])buffer, 0, buffer.length);
//    }
//
//    public static void readFully(InputStream input, byte[] buffer, int offset, int length) throws IOException {
//        int actual = read(input, buffer, offset, length);
//        if (actual != length) {
//            throw new EOFException("Length to read: " + length + " actual: " + actual);
//        }
//    }
//
//    public static void readFully(InputStream input, byte[] buffer) throws IOException {
//        readFully((InputStream)input, (byte[])buffer, 0, buffer.length);
//    }
//
//    static {
//        DIR_SEPARATOR = File.separatorChar;
//    }
//}
//
