package com.aio.portable.swiss.suite.storage.db.orm;

public class Type {
    public static String type2Property(String type){
        if(type.matches("(?i)VARCHAR|(?i)CHAR|(?i)TEXT")){
            return "String";
        }
        if(type.indexOf("BLOB")!=-1){
            return "byte[]";
        }
        if(type.matches("(?i)DATE|(?i)TIME|(?i)DATETIME|(?i)TIMESTAMP|(?i)YEAR")){
            return "Date";
        }
        if(type.matches("(?i)INT|(?i)TINYINT|(?i)SMALLINT|(?i)BIGINT|(?i)DECIMAL")){
            return "Integer";
        }
        return type;
    }


}
