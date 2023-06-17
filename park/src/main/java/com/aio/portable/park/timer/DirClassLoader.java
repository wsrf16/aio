package com.aio.portable.park.timer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class DirClassLoader extends ClassLoader {
    public DirClassLoader() {
        super(ClassLoader.getSystemClassLoader());
    }

    private File objFile;

    public File getObjFile() {
        return objFile;
    }

    public void setObjFile(File objFile) {
        this.objFile = objFile;
    }

//    @Override
//    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
//        synchronized (getClassLoadingLock(name)) {
//            // First, check if the class has already been loaded
//            Class<?> c = findLoadedClass(name);
//            if (c == null) {
//                long t0 = System.nanoTime();
//                if (c == null) {
//                    // If still not found, then invoke findClass in order
//                    // to find the class.
//                    long t1 = System.nanoTime();
//                    c = findClass(name);
//
//                    // this is the defining class loader; record the stats
//                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
//                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
//                    sun.misc.PerfCounter.getFindClasses().increment();
//                }
//            }
//            if (resolve) {
//                resolveClass(c);
//            }
//            return c;
//        }
//    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //这个classLoader的主要方法
        System.out.println("findClass-findClass-findClass-findClass");
        Class clazz = null;
        try {
            byte[] data = getClassFileBytes(getObjFile());
//            clazz = defineClass(name, data, 0, data.length);//这个方法非常重要
            if (null == clazz) {//如果在这个类加载器中都不能找到这个类的话，就真的找不到了

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;

    }

    /**
     * 把CLASS文件转成BYTE
     *
     * @throws Exception
     */
    private byte[] getClassFileBytes(File file) throws Exception {
        //采用NIO读取
        FileInputStream fis = new FileInputStream(file);
        FileChannel fileC = fis.getChannel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableByteChannel outC = Channels.newChannel(baos);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            int i = fileC.read(buffer);
            if (i == 0 || i == -1) {
                break;
            }
            buffer.flip();
            outC.write(buffer);
            buffer.clear();
        }
        fis.close();
        return baos.toByteArray();
    }


}
