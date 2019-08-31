package com.aio.portable.swiss.sugar;

import java.io.*;

/**
 * Created by York on 2017/11/28.
 */
public class FileUtils {
    public static String readFileForText(String path) {
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

    public static byte[] readFileForByte(String path) {
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

    public static void writeFile(String path, String content) {
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

    public static void writeFile(String path, byte[] bytes) {
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

    public static boolean delete(File fileOrDirectory) {
        boolean hasDeleted = false;
        if (fileOrDirectory.exists()) {
            if (fileOrDirectory.isDirectory()) {
                File files[] = fileOrDirectory.listFiles();
                for (int i = 0; i < files.length; i++) {
                    delete(files[i]);
                }
            }
            hasDeleted = true;
            fileOrDirectory.delete();
        }
        return hasDeleted;
    }
}
