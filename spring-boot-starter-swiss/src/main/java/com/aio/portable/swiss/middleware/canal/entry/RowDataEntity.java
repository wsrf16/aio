package com.aio.portable.swiss.middleware.canal.entry;

import com.aio.portable.swiss.sugar.ThrowableSugar;
import com.aio.portable.swiss.suite.bean.DeepCloneSugar;
import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RowDataEntity<T> {
    private List<ColumnEntity> beforeColumnEntityList;
    private List<ColumnEntity> afterColumnEntityList;
    private T beforeRowModel;
    private T afterRowModel;

    public List<ColumnEntity> getBeforeColumnEntityList() {
        return beforeColumnEntityList;
    }

    public void setBeforeColumnEntityList(List<ColumnEntity> beforeColumnEntityList) {
        this.beforeColumnEntityList = beforeColumnEntityList;
    }

    public List<ColumnEntity> getAfterColumnEntityList() {
        return afterColumnEntityList;
    }

    public void setAfterColumnEntityList(List<ColumnEntity> afterColumnEntityList) {
        this.afterColumnEntityList = afterColumnEntityList;
    }

    public T getBeforeRowModel() {
        return beforeRowModel;
    }

    public void setBeforeRowModel(T beforeRowModel) {
        this.beforeRowModel = beforeRowModel;
    }

    public T getAfterRowModel() {
        return afterRowModel;
    }

    public void setAfterRowModel(T afterRowModel) {
        this.afterRowModel = afterRowModel;
    }




    public RowDataEntity(List<ColumnEntity> beforeColumnsList, List<ColumnEntity> afterColumnsList, Class<T> clazz) {
        beforeColumnEntityList = beforeColumnsList;
        afterColumnEntityList = afterColumnsList;
        beforeRowModel = parseBeforeRowModel(clazz);
        afterRowModel = parseAfterRowModel(clazz);
    }

    public RowDataEntity(List<ColumnEntity> beforeColumnEntityList, List<ColumnEntity> afterColumnEntityList) {
        this.beforeColumnEntityList = beforeColumnEntityList;
        this.afterColumnEntityList = afterColumnEntityList;
    }

    public static List<ColumnEntity> toColumnEntityList(List<CanalEntry.Column> columnList) {
        List<ColumnEntity> collect = columnList.stream().map(c -> {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setName(c.getName());
            columnEntity.setValue(c.getValue());
            columnEntity.setUpdated(c.getUpdated());
            return columnEntity;
        }).collect(Collectors.toList());
        return collect;
    }

    private static <T> T toRowModel(List<ColumnEntity> columnsList, Class<T> clazz) {
        return ThrowableSugar.throwRuntimeExceptionIfCatch(() -> {
            Map<String, Object> map = columnsList.stream().collect(Collectors.toMap(c -> c.getName(), c -> c.getValue()));
            T t = DeepCloneSugar.Json.clone(map, clazz);
            return t;
        });
    }

    private  <T> T parseBeforeRowModel(Class<T> clazz) {
        return toRowModel(beforeColumnEntityList, clazz);
    }

    private  <T> T parseAfterRowModel(Class<T> clazz) {
        return toRowModel(afterColumnEntityList, clazz);
    }

//    public <T> RowModel<T> parseRowModel(Class<T> clazz, CanalEntry.EventType entryType) {
//        final RowModel<T> rowModel = new RowModel<T>(this.parseBeforeRowModel(clazz), this.parseAfterRowModel(clazz), entryType);
//        return rowModel;
//    }


}