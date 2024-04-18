package com.aio.portable.swiss.suite.io.listen;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class WatchServiceThread {
    private Path path;

    private WatchService watchService;

    private static ExecutorService fixedThreadPool = Executors.newCachedThreadPool();

    private static Listener listener;

    public WatchServiceThread(String directory){
        try {
            this.watchService = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        this.path = Paths.get(directory);
        this.listener = new Listener(path, watchService);
    }

    public void listen() {
        listen(StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
    }

    public void listen(WatchEvent.Kind<?>... events) {
        try {
            path.register(watchService, events);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        fixedThreadPool.execute(listener);
    }

    public HashMap<WatchEvent.Kind<Path>, Consumer<File>> getHandlerMap() {
        return listener.getHandlerMap();
    }

    public void setModifyHandler(Consumer<File> consumer) {
        listener.getHandlerMap().put(StandardWatchEventKinds.ENTRY_MODIFY, consumer);
    }

    public void setCreateHandler(Consumer<File> consumer) {
        listener.getHandlerMap().put(StandardWatchEventKinds.ENTRY_CREATE, consumer);
    }

    public void setDeleteHandler(Consumer<File> consumer) {
        listener.getHandlerMap().put(StandardWatchEventKinds.ENTRY_DELETE, consumer);
    }

    public void setHandler(Consumer<File> consumer) {
        listener.getHandlerMap().put(null, consumer);
    }


    public class Listener implements Runnable {
        private WatchService watchService;
        private Path path;
        private final HashMap<WatchEvent.Kind<Path>, Consumer<File>> handlerMap;

        public HashMap<WatchEvent.Kind<Path>, Consumer<File>> getHandlerMap() {
            return handlerMap;
        }

        public void setModifyHandler(Consumer<File> consumer) {
            handlerMap.put(StandardWatchEventKinds.ENTRY_MODIFY, consumer);
        }

        public void setCreateHandler(Consumer<File> consumer) {
            handlerMap.put(StandardWatchEventKinds.ENTRY_CREATE, consumer);
        }

        public void setDeleteHandler(Consumer<File> consumer) {
            handlerMap.put(StandardWatchEventKinds.ENTRY_DELETE, consumer);
        }

        public void setAnyHandler(Consumer<File> consumer) {
            handlerMap.put(null, consumer);
        }

        public Listener(Path path, WatchService watchService) {
            this.path = path;
            this.watchService = watchService;
            HashMap<WatchEvent.Kind<Path>, Consumer<File>> handlerMap = new HashMap<>();
            handlerMap.put(StandardWatchEventKinds.ENTRY_MODIFY, null);
            handlerMap.put(StandardWatchEventKinds.ENTRY_CREATE, null);
            handlerMap.put(StandardWatchEventKinds.ENTRY_DELETE, null);
            handlerMap.put(null, null);
            this.handlerMap = handlerMap;
        }

        @Override
        public void run() {
            long lastModified = 0;
            while (true) {
                try {
                    WatchKey watchKey = watchService.take();
                    for (WatchEvent<?> event : watchKey.pollEvents()) {
                        Path changedRelativePath = (Path) event.context();
                        Path changedAbsolutePath = path.resolve(changedRelativePath);
                        File file = changedAbsolutePath.toFile();
                        // 利用文件时间戳，防止触发两次
                        if (lastModified != file.lastModified()) {
                            if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                                Consumer<File> consumer = handlerMap.get(StandardWatchEventKinds.ENTRY_MODIFY);
                                if (consumer != null)
                                    consumer.accept(file);
                            } else if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                                Consumer<File> consumer = handlerMap.get(StandardWatchEventKinds.ENTRY_CREATE);
                                if (consumer != null)
                                    consumer.accept(file);
                            } else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                                Consumer<File> consumer = handlerMap.get(StandardWatchEventKinds.ENTRY_DELETE);
                                if (consumer != null)
                                    consumer.accept(file);
                            }

                            {
                                Consumer<File> consumer = handlerMap.get(null);
                                if (consumer != null)
                                    consumer.accept(file);
                            }
                            lastModified = file.lastModified();
                        }
                    }
                    watchKey.reset();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

//    public void close() {
//        try {
//            watchService.();
//            watchService.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
}
