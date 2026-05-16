package com.aio.portable.swiss.suite.storage.db;

import java.util.List;

public abstract class SQLSugar {
    private static final String COLUMN_NAME_WITH_BACKTICKS_REGEX = "^`?[a-zA-Z_][a-zA-Z0-9_]*`?$";

    public static boolean verifyColumnName(String columnName) {
        return columnName == null || !columnName.matches(COLUMN_NAME_WITH_BACKTICKS_REGEX);
    }

    public static boolean verifyColumnName(List<String> columnNameList) {
        return columnNameList.stream().allMatch(SQLSugar::verifyColumnName);
    }
}
