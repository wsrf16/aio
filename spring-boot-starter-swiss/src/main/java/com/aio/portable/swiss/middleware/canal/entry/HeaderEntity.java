package com.aio.portable.swiss.middleware.canal.entry;

import com.alibaba.otter.canal.protocol.CanalEntry;

public class HeaderEntity {
    private String logfileName;
    private long logfileOffset;
    private String serverEncode;
    private long executeTime;
    private CanalEntry.EventType eventType;
    private int version;
    private long eventLength;
    private long serverId;
    private String schemaName;
    private String tableName;

    public String getLogfileName() {
        return logfileName;
    }

    public void setLogfileName(String logfileName) {
        this.logfileName = logfileName;
    }

    public long getLogfileOffset() {
        return logfileOffset;
    }

    public void setLogfileOffset(long logfileOffset) {
        this.logfileOffset = logfileOffset;
    }

    public String getServerEncode() {
        return serverEncode;
    }

    public void setServerEncode(String serverEncode) {
        this.serverEncode = serverEncode;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    public CanalEntry.EventType getEventType() {
        return eventType;
    }

    public void setEventType(CanalEntry.EventType eventType) {
        this.eventType = eventType;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getEventLength() {
        return eventLength;
    }

    public void setEventLength(long eventLength) {
        this.eventLength = eventLength;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public static HeaderEntity toHeader(CanalEntry.Header header) {
        HeaderEntity headerEntity = new HeaderEntity();
        headerEntity.setLogfileName(header.getLogfileName());
        headerEntity.setLogfileOffset(header.getLogfileOffset());
        headerEntity.setServerEncode(header.getServerenCode());
        headerEntity.setExecuteTime(header.getExecuteTime());
        headerEntity.setEventType(header.getEventType());
        headerEntity.setVersion(header.getVersion());
        headerEntity.setEventLength(header.getEventLength());
        headerEntity.setServerId(header.getServerId());
        headerEntity.setSchemaName(header.getSchemaName());
        headerEntity.setTableName(header.getTableName());
        return headerEntity;
    }
}