package com.aio.portable.swiss.sugar.meta;

import com.aio.portable.swiss.suite.io.IOSugar;

import java.io.*;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class StreamClassLoader extends ClassLoader {

    String file;

    private StreamClassLoader(String file) {
        this.file = file;
    }

    /**
     * buildByResource
     * @param name : com/art/Book.class
     * @return
     */
    public static final StreamClassLoader buildByResource(String name) {
        URL url = StreamClassLoader.class.getClassLoader().getResource(name);
        return new StreamClassLoader(url.getPath());
    }

    /**
     * buildByFile
     * @param file : target/classes/com/art/Book.class  |  art-1.0-SNAPSHOT.jar
     * @return
     */
    public static final StreamClassLoader buildByFile(String file) {
        return new StreamClassLoader(file);
    }

    public Class<?> loadClassByBinary(String className) throws ClassNotFoundException {
        if (file.toLowerCase().trim().endsWith(".class")) {
            byte[] b = IOSugar.Files.readFileForByte(file);
            return defineClass(className, b, 0, b.length);
        } else if (file.toLowerCase().trim().endsWith(".jar")) {
            JarFile jarFile;
            String _name = className.replace(".", "/") + ".class";
            try {
                jarFile = new JarFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            List<JarEntry> jarEntryList = Collections.list(jarFile.entries());
            return jarEntryList.stream().filter(c -> !c.isDirectory() && c.getName().toLowerCase().trim().endsWith(".class") && c.getName().trim().equals(_name)).map(c -> {
                InputStream inputStream = null;
                try {
                    //"jar:file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/com.art.Book.class"
                    String jarURL = new File(file).toURI().toURL().toExternalForm();
                    inputStream = new URL("jar:" + jarURL + "!/"+ _name).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                URL url = this.getClass().getResource("/" + _name);
                byte[] b = IOSugar.Streams.toByteArray(inputStream);
                return defineClass(className, b, 0, b.length);
            }).findFirst().get();
        }
        return null;
    }

//    private static byte[] loadClassData(String file) {
//        InputStream in = null;
//        try {
//            in = new FileInputStream(new File(file));
//            return loadClassData(in);
//        } catch (Exception e) {
////            e.getStackTrace();
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                if (in != null)
//                    in.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    private static byte[] loadClassData(InputStream in) {
//        ByteArrayOutputStream out = null;
//        try {
//            out = new ByteArrayOutputStream();
//            int i;
//            while ((i = in.read()) != -1) {
//                out.write(i);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                out.close();
//                in.close();
//            } catch (IOException e) {
////                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//        }
//        return out.toByteArray();
//    }


}
