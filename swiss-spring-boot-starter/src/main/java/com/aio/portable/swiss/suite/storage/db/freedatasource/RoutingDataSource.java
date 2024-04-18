package com.aio.portable.swiss.suite.storage.db.freedatasource;

import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.text.MessageFormat;

public class RoutingDataSource extends AbstractRoutingDataSource {
//    private static final Log log = LogFactory.getLog(RoutingDataSource.class);
    private static final LocalLog log = LocalLog.getLog(RoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        String datasource = DataSourceHolder.getDataSource();
        log.debug(MessageFormat.format("current datasource key is {0}", datasource));
        return datasource;
    }
}
