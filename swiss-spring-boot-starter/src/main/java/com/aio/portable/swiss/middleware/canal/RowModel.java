package com.aio.portable.swiss.middleware.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;

public class RowModel<T> {
    private T beforeRowModel;
    private T afterRowModel;
    private CanalEntry.EventType entryType;

    public RowModel(T beforeRowModel, T afterRowModel, CanalEntry.EventType entryType) {
        this.beforeRowModel = beforeRowModel;
        this.afterRowModel = afterRowModel;
        this.entryType = entryType;
    }

    public T getBeforeRowModel() {
        return beforeRowModel;
    }

    public T getAfterRowModel() {
        return afterRowModel;
    }

    public CanalEntry.EventType getEntryType() {
        return entryType;
    }
}
