package com.aio.portable.swiss.suite.storage.db.freedatasource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.text.MessageFormat;

public class RoutingDataSource extends AbstractRoutingDataSource {
    private final static Log log = LogFactory.getLog(RoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        String datasource = DataSourceHolder.getDataSource();
        log.debug(MessageFormat.format("current datasource key is {0}", datasource));
        return datasource;
    }
}
