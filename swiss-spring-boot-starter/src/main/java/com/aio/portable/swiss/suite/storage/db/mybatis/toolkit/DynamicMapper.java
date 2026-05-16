package com.aio.portable.swiss.suite.storage.db.mybatis.toolkit;

import com.aio.portable.swiss.suite.storage.db.SQLSugar;
import com.aio.portable.swiss.suite.storage.db.mybatis.EnhanceBaseMapper;
import com.google.common.collect.Lists;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface DynamicMapper<S> extends EnhanceBaseMapper<S> {
    @Insert("<script>" +
            "INSERT INTO ${tableName} " +
            "(" +
            "  <foreach collection='columnNameList' item='value' separator=','>" +
            "    `${value}`" +
            "  </foreach>" +
            ") " +
            "VALUES " +
            "  <foreach collection='rowList' item='line' separator=','>" +
            "    (" +
            "      <foreach collection='line' item='value' separator=','>" +
            "        #{value}" +
            "      </foreach>" +
            "    )" +
            "  </foreach>" +
            "</script>")
    int insertBatchMap(String tableName, List<String> columnNameList, List<List<Object>> rowList);

    default int insertBatchMapPartition(String tableName, List<String> columnNameList, List<List<Object>> mapList) {

        boolean valid = SQLSugar.verifyColumnName(columnNameList);
        if (!valid) {
            throw new IllegalArgumentException("Illegal column name format.");
        }

        List<List<List<Object>>> partition = Lists.partition(mapList, 5000);
        int count = 0;
        for (List<List<Object>> rowList : partition) {
            count += this.insertBatchMap(tableName, columnNameList, rowList);
        }
        return count;
    }


}
