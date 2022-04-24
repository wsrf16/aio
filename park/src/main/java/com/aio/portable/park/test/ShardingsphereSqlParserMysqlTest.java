package com.aio.portable.park.test;

import com.aio.portable.swiss.sugar.type.CollectionSugar;
import org.apache.shardingsphere.sql.parser.api.CacheOption;
import org.apache.shardingsphere.sql.parser.api.SQLParserEngine;
import org.apache.shardingsphere.sql.parser.api.SQLVisitorEngine;
import org.apache.shardingsphere.sql.parser.core.ParseContext;
import org.apache.shardingsphere.sql.parser.spi.DatabaseTypedSQLParserFacade;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;

@Configuration
public class ShardingsphereSqlParserMysqlTest {
    public void todo() {
        org.apache.shardingsphere.sql.parser.mysql.parser.MySQLParserFacade ll;
        List<DatabaseTypedSQLParserFacade> databaseTypedSQLParserFacades = CollectionSugar.toList(ServiceLoader.load(DatabaseTypedSQLParserFacade.class).iterator());
        String sql = "select order_id from t_order where status = 'OK'";
        CacheOption cacheOption = new CacheOption(128, 1024L, 4);
        SQLParserEngine parserEngine = new SQLParserEngine("MySQL",
                cacheOption, false);
        ParseContext parseContext = parserEngine.parse(sql, false);
        SQLVisitorEngine visitorEngine = new SQLVisitorEngine("MySQL",
                "FORMAT", new Properties());
        String result = visitorEngine.visit(parseContext);
        System.out.println(result);
        System.out.println(result);
    }
}
