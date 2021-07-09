package com.aio.portable.swiss.middleware.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SqlLog {
    private List<Entry> entryList;
    private Map<String, Class<?>> tableModelMapping = new HashMap<>();

    public SqlLog(List<Entry> entryList) {
        this.entryList = entryList;
    }

    public List<Entry> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<Entry> entryList) {
        this.entryList = entryList;
    }

    public Map<String, Class<?>> getTableModelMapping() {
        return tableModelMapping;
    }

    public void setTableModelMapping(Map<String, Class<?>> tableModelMapping) {
        this.tableModelMapping = tableModelMapping;
    }

    public List<EntryEntity> toEntryEntityList() {
        List<EntryEntity> entryEntityList = toComplicatedEntryEntityList().stream()
                .filter(entry -> entry.getEntryType() != CanalEntry.EntryType.TRANSACTIONBEGIN
                        && entry.getEntryType() != CanalEntry.EntryType.TRANSACTIONEND)
                .collect(Collectors.toList());
        return entryEntityList;
    }

    public List<EntryEntity> toComplicatedEntryEntityList() {
        List<EntryEntity> entryEntityList = entryList.stream()
                .map(entry -> {
                    final String tableName = entry.getHeader().getTableName();
                    final Class clazz = tableModelMapping.get(tableName);
                    if (!StringUtils.isEmpty(tableName) && clazz == null){
                        new ClassNotFoundException("Not found class of table(" + tableName + ")").printStackTrace();
                    }
                    final EntryEntity entryEntity = EntryEntity.toEntryEntity(entry, clazz);
                    return entryEntity;
                })
                .collect(Collectors.toList());
        return entryEntityList;
    }

//    public <T> List<List<RowModel<T>>> parseRowModel(Class<T> clazz) {
//        List<List<RowModel<T>>> collect = entryList.stream()
//                .map(entry -> EntryEntity.toEntryEntity(entry).parseRowModel(clazz))
//                .collect(Collectors.toList());
//        return collect;
//    }

//    public List<List<RowModel<?>>> parseRowModel() {
//        List<List<RowModel<?>>> collect = entryList.stream()
//                .map(entry -> {
//                    final String tableName = entry.getHeader().getTableName();
//                    final Class clazz = tableModelMapping.get(tableName);
//                    if (clazz == null){
//                        new ClassNotFoundException("Not found class of table(" + tableName + ")").printStackTrace();
//                        return null;
//                    } else {
//                        final EntryEntity entryEntity = EntryEntity.toEntryEntity(entry, clazz);
//                        final List<RowModel<?>> rowModels = (List<RowModel<?>>) entryEntity;
//                        return rowModels;
//                    }
//                })
//                .filter(c -> c != null)
//                .collect(Collectors.toList());
//        return collect;
//    }
}
