package com.aio.portable.swiss.middleware.canal.entry;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.List;
import java.util.stream.Collectors;

public class ColumnEntity {
    private String name;
    private String value;
    private boolean isUpdated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }
}