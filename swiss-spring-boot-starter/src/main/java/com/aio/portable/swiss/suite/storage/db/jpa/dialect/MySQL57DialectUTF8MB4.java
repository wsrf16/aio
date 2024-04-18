package com.aio.portable.swiss.suite.storage.db.jpa.dialect;

import org.hibernate.dialect.MySQL57Dialect;

public class MySQL57DialectUTF8MB4 extends MySQL57Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
    }
}