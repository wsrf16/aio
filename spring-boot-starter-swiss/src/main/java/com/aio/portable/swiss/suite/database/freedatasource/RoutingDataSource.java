package com.aio.portable.swiss.suite.database.freedatasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.text.MessageFormat;

public class RoutingDataSource extends AbstractRoutingDataSource {
    public final static Logger logger = LoggerFactory.getLogger(RoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        String datasource = DataSourceHolder.getDataSource();
        logger.debug(MessageFormat.format("current datasource key is {0}", datasource));
        return datasource;
    }
}
