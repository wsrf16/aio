package com.aio.portable.swiss.suite.db.ddl;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.sugar.type.CollectionSugar;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JavaClassToDDLGenerator {

    private static final Map<Class<?>, String> dataTypes = new HashMap<>();

    static {
        dataTypes.put(int.class, "INT");
        dataTypes.put(Integer.class, "INT");
        dataTypes.put(long.class, "BIGINT");
        dataTypes.put(Long.class, "BIGINT");
        dataTypes.put(double.class, "DOUBLE");
        dataTypes.put(Double.class, "DOUBLE");
        dataTypes.put(String.class, "VARCHAR(255)");
        dataTypes.put(boolean.class, "TINYINT");
        dataTypes.put(Boolean.class, "TINYINT");
        dataTypes.put(Date.class, "DATETIME");
        dataTypes.put(BigDecimal.class, "NUMERIC");
        // Add more data type mappings
    }

    public static String generateDDLFromClass(Class<?>... clazz) {
        List<String> ddlList = generateDDLFromClassList(clazz);
        String ddl = ddlList.stream().reduce((prev, current) -> prev + Constant.LINE_SEPARATOR + Constant.LINE_SEPARATOR + current).get();
        return ddl;
    }

    public static List<String> generateDDLFromClassList(Class<?>... clazz) {
        if (clazz == null) {
            throw new NullPointerException();
        }
        List<String> collect = Arrays.stream(clazz).map(JavaClassToDDLGenerator::generateDDLFromClass).collect(Collectors.toList());
        return collect;
    }

    public static String generateDDLFromClass(Class<?> clazz) {
        StringBuilder ddlBuilder = new StringBuilder();

        String tableName = clazz.getSimpleName();
        ddlBuilder.append("CREATE TABLE ").append(tableName).append(" (\n");

        Field[] fields = clazz.getDeclaredFields();
        List<String> columnDefinitions = new ArrayList<>();
        String primaryKey = null;

        for (Field field : fields) {
            String columnName = field.getName();
            String columnType = getColumnType(field.getType());
            String columnDefinition = columnName + " " + columnType;

            if (field.isAnnotationPresent(PrimaryKey.class)) {
                primaryKey = columnName;
                columnDefinition += " AUTO_INCREMENT";
            }

            if (field.isAnnotationPresent(ForeignKey.class)) {
                ForeignKey fkAnnotation = field.getAnnotation(ForeignKey.class);
                String referencedTable = fkAnnotation.table();
                String referencedColumn = fkAnnotation.column();
                columnDefinition += " REFERENCES " + referencedTable + "(" + referencedColumn + ")";
            }

            if (field.isAnnotationPresent(Unique.class)) {
                columnDefinition += " UNIQUE";
            }

            columnDefinitions.add(columnDefinition);
        }

        ddlBuilder.append(String.join(",\n", columnDefinitions));

        if (primaryKey != null) {
            ddlBuilder.append(",\nPRIMARY KEY (").append(primaryKey).append(")");
        }

        ddlBuilder.append("\n);");

        return ddlBuilder.toString();
    }

    private static String getColumnType(Class<?> fieldType) {
        String dataType = dataTypes.get(fieldType);
        if (dataType != null) {
            return dataType;
        }
        return "VARCHAR(255)";
    }
}

