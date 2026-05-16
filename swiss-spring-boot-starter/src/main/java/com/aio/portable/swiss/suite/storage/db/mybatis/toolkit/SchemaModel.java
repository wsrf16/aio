package com.aio.portable.swiss.suite.storage.db.mybatis.toolkit;

import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class SchemaModel {
    @JsonProperty("TABLE_CATALOG")
    private String tableCatalog;

    @JsonProperty("IS_NULLABLE")
    private String isNullable;

    @JsonProperty("TABLE_NAME")
    private String tableName;

    @JsonProperty("TABLE_SCHEMA")
    private String tableSchema;

    @JsonProperty("EXTRA")
    private String extra;

    @JsonProperty("COLUMN_NAME")
    private String columnName;

    @JsonProperty("COLUMN_KEY")
    private String columnKey;

    @JsonProperty("NUMERIC_PRECISION")
    private String numericPrecision;

    @JsonProperty("PRIVILEGES")
    private String privileges;

    @JsonProperty("COLUMN_COMMENT")
    private String columnComment;

    @JsonProperty("NUMERIC_SCALE")
    private String numericScale;

    @JsonProperty("COLUMN_TYPE")
    private String columnType;

    @JsonProperty("GENERATION_EXPRESSION")
    private String generationExpression;

    @JsonProperty("ORDINAL_POSITION")
    private String ordinalPosition;

    @JsonProperty("DATA_TYPE")
    private String dataType;

    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getNumericPrecision() {
        return numericPrecision;
    }

    public void setNumericPrecision(String numericPrecision) {
        this.numericPrecision = numericPrecision;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getNumericScale() {
        return numericScale;
    }

    public void setNumericScale(String numericScale) {
        this.numericScale = numericScale;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getGenerationExpression() {
        return generationExpression;
    }

    public void setGenerationExpression(String generationExpression) {
        this.generationExpression = generationExpression;
    }

    public String getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(String ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public static SchemaModel getOneBy(List<SchemaModel> schema, String key, Object value) {
        SchemaModel first = CollectionSugar.findFirst(schema, key, value);


        return schema.stream().filter(c -> Objects.equals(ClassSugar.PropertyDescriptors.getPropertyValue(c, key), value) ? true : false).findFirst().orElse(null);
    }
}
