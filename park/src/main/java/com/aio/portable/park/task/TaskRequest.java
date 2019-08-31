package com.aio.portable.park.task;

import java.util.Map;
import java.util.TreeMap;

public class TaskRequest
    {
        public RequestStatusEnum status = RequestStatusEnum.CollectElement;
        //可存放每一步的临时结果
        public Map<RequestStatusEnum, Object> storage = new TreeMap<>();

        public static TaskRequest createInstance(RequestStatusEnum status)
        {
            return new TaskRequest() { {
                status = status;} };
        }

        public boolean HasStorge(RequestStatusEnum status)
        {
            return storage.containsKey(status);
        }
}
