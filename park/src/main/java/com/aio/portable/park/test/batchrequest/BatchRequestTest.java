package com.aio.portable.park.test.batchrequest;

import com.aio.portable.park.common.UserInfoEntity;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.hamlet.bean.ResponseWrappers;
import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.concurrent.*;

public class BatchRequestTest {
    static class BatchRequest<T> {
        String id = IDS.uuid();
        T object;
        CompletableFuture<BatchResponse<T>> future;

        public String getId() {
            return id;
        }

        public CompletableFuture<BatchResponse<T>> getFuture() {
            return future;
        }

        public void setFuture(CompletableFuture<BatchResponse<T>> future) {
            this.future = future;
        }

        public void setId(String id) {
            this.id = id;
        }

        public T getObject() {
            return object;
        }

        public void setObject(T object) {
            this.object = object;
        }

        public BatchRequest(T object) {
            this.object = object;
        }
    }
    static class BatchResponse<T> {
        String id = IDS.uuid();
        BatchRequest<T> object;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public BatchRequest<T> getObject() {
            return object;
        }

        public void setObject(BatchRequest<T> object) {
            this.object = object;
        }

        public BatchResponse(BatchRequest<T> object) {
            this.object = object;
        }
    }

    public BatchRequestTest() {
        init();
    }

    Queue<BatchRequest> queue = new LinkedBlockingDeque<>();

    public CompletableFuture<BatchResponse<UserInfoEntity>> handle(UserInfoEntity userInfo) {
        BatchRequest<UserInfoEntity> batchRequest = new BatchRequest<>(userInfo);
        batchRequest.setObject(userInfo);
        queue.add(batchRequest);

//        CompletableFuture<BatchResponse<UserInfoEntity>> future = CompletableFuture.supplyAsync(() -> {
//            queue.add(batchRequest);
//        });
        CompletableFuture<BatchResponse<UserInfoEntity>> future = new CompletableFuture();
        batchRequest.setFuture(future);

        return future;
    }

    public ResponseWrapper<BatchResponse> batch(UserInfoEntity userInfo) {

        try {
            BatchResponse<UserInfoEntity> batchResponse = handle(userInfo).get();
            return ResponseWrappers.build(0, batchResponse);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    ScheduledExecutorService executorService =
//            Executors.newScheduledThreadPool(1);

            new ScheduledThreadPoolExecutor(1,
                    new BasicThreadFactory.
                            Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

    ScheduledFuture<?> scheduledFuture;
    public void init() {
        //        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 10000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1));
        new ThreadPoolExecutor(2, 5, 10000, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1));


        scheduledFuture = executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Map<String, BatchRequest> request = new HashMap<>();
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    BatchRequest batchRequest = queue.poll();
                    request.put(batchRequest.getId(), batchRequest);
                }
                Map<String, BatchResponse> result = batchCall(request);
                for (int i = 0; i < size; i++) {
                    result.entrySet().stream().forEach(c-> {
//                        request.put(c.getKey(), c.getValue());
                        String key = c.getKey();
                        request.get(key).getFuture().complete(c.getValue());
                    });

                }
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);
    }



    private Map<String, BatchResponse> batchCall(Map<String, BatchRequest> map) {
        return map.entrySet().stream()
//                .map(c -> new BatchResponse<UserInfoEntity>(c.getValue()))
                .collect(Collectors.toMap(it -> it.getKey(), it -> new BatchResponse<>(it.getValue())));
    }
}
