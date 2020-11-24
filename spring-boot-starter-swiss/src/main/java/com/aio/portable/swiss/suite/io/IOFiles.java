package com.aio.portable.swiss.suite.io;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by York on 2017/11/28.
 */
public abstract class IOFiles {
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

//    public final static void createDirectoryIfNotExists(Path path) throws IOException {
//        if (Files.notExists(path))
//            Files.createDirectory(path);
//    }
//
//    public final static void createDirectoryIfNotExists(File directory) throws IOException {
//        Path path = directory.toPath();
//        if (Files.notExists(path))
//            Files.createDirectory(path);
//    }

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
