package com.aio.portable.swiss.middleware.canal;

import com.aio.portable.swiss.middleware.canal.entry.ColumnEntity;
import com.aio.portable.swiss.middleware.canal.entry.HeaderEntity;
import com.aio.portable.swiss.middleware.canal.entry.RowDataEntity;
import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.List;
import java.util.stream.Collectors;

public class EntryEntity {
    private HeaderEntity header;
    private boolean isDdl;
    private String sql;
    private CanalEntry.EntryType entryType;
    private List<RowDataEntity> rowDataEntityList;

    public HeaderEntity getHeader() {
        return header;
    }

    public void setHeader(HeaderEntity header) {
        this.header = header;
    }

    public boolean isDdl() {
        return isDdl;
    }

    public void setDdl(boolean ddl) {
        isDdl = ddl;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public CanalEntry.EntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(CanalEntry.EntryType entryType) {
        this.entryType = entryType;
    }

    public List<RowDataEntity> getRowDataEntityList() {
        return rowDataEntityList;
    }

    public void setRowDataEntityList(List<RowDataEntity> rowDataEntityList) {
        this.rowDataEntityList = rowDataEntityList;
    }

//    private static CanalEntry.EntryType toEntryType(CanalEntry.EntryType entryType) {
//        return CanalEntry.EntryType.valueOf(entryType.getNumber());
//    }

//    public <T> List<RowModel<T>> parseRowModel(Class<T> clazz) {
//        final List<RowModel<T>> collect = rowDataEntityList.stream().map(c -> c.parseRowModel(clazz, header.getEventType())).collect(Collectors.toList());
//        return collect;
//    }

    public static <T> EntryEntity toEntryEntity(CanalEntry.Entry entry) {
        return toEntryEntity(entry, null);
    }

    public static <T> EntryEntity toEntryEntity(CanalEntry.Entry entry, Class<T> clazz) {
        try {
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());

            EntryEntity entryEntity = new EntryEntity();
            entryEntity.setDdl(rowChange.getIsDdl());
            entryEntity.setSql(rowChange.getSql());
            entryEntity.setEntryType(entry.getEntryType());
            entryEntity.setHeader(HeaderEntity.toHeader(entry.getHeader()));
            List<RowDataEntity> rowDataEntityList = rowChange.getRowDatasList().stream().map(c -> {
                List<CanalEntry.Column> beforeColumnsList = c.getBeforeColumnsList();
                List<CanalEntry.Column> afterColumnsList = c.getAfterColumnsList();

                RowDataEntity rowDataEntity = clazz == null ? new RowDataEntity(
                        RowDataEntity.toColumnEntityList(beforeColumnsList),
                        RowDataEntity.toColumnEntityList(afterColumnsList)
                ) : new RowDataEntity(
                        RowDataEntity.toColumnEntityList(beforeColumnsList),
                        RowDataEntity.toColumnEntityList(afterColumnsList),
                        clazz
                );
                return rowDataEntity;
            }).collect(Collectors.toList());
            entryEntity.setRowDataEntityList(rowDataEntityList);
            return entryEntity;
        } catch (Exception e) {
            throw new RuntimeException("ERROR ## parser of StoreValue has an error, data:" + entry.toString(), e);
        }
    }


}