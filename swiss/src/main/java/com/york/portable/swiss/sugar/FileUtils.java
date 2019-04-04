package com.york.portable.swiss.sugar;

import java.io.*;

/**
 * Created by York on 2017/11/28.
 */
public class FileUtils {
    public static String readFileForText(String filePathAndName) {
        StringBuffer sb = null;
        try {
            File f = new File(filePathAndName);
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

    public static byte[] readFileForByte(String filePathAndName) {
        try {
            FileInputStream fis = null;
            fis = new FileInputStream(filePathAndName);

            byte[] bytes = new byte[(int) new File(filePathAndName).length()];
            fis.read(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeFile(String filePathAndName, String fileContent) {
        try {
            File f = new File(filePathAndName);
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(fileContent);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String filePathAndName, byte[] bytes) {
        try {
            File file = new File(filePathAndName);
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

    public static boolean deleteFile(File file, boolean recursion) {
        boolean hasDeleted = false;
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory() && recursion) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i], recursion);
                }
            }
            hasDeleted = true;
            file.delete();
        } else {
            hasDeleted = false;
        }
        return hasDeleted;
    }
}
