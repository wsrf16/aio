package com.aio.portable.swiss.suite.log.solution.elk.rabbit;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.facade.LogPrinter;
import com.aio.portable.swiss.suite.log.solution.elk.ESLogRecordItem;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.aio.portable.swiss.suite.log.solution.slf4j.Slf4JLogProperties;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogRecordItem;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by York on 2017/11/23.
 */
public class RabbitMQLog extends LogSingle {
//    private static final Log log = LogFactory.getLog(RabbitMQLog.class);
    private static final LocalLog log = LocalLog.getLog(RabbitMQLog.class);

    protected RabbitMQLogProperties properties;

    public RabbitMQLogProperties getProperties() {
        return properties;
    }


    public RabbitMQLog(String name) {
        super(name);
    }

    public RabbitMQLog(Class<?> clazz) {
        this(clazz.toString());
    }

    public RabbitMQLog() {
        this(StackTraceSugar.Previous.getClassName());
    }

    @Override
    protected LogPrinter buildPrinter() {
        this.properties = this.properties == null ? RabbitMQLogProperties.getSingleton() : this.properties;
        return RabbitMQLogPrinter.instance(this.getName(), this.properties);
    }

    @Override
    protected void output(LogRecordItem logRecordItem) {
        ESLogRecordItem converted = convert(logRecordItem);
        LevelEnum level = converted.getLevel();
        String esIndex = converted.getEsIndex();
        if (esIndex == null) {
            throw new NullPointerException("esIndex");
        } else if (esIndex.contains(":")) {
            String key = esIndex.split(":")[0];
            String val = ESLogRecordItem.formatIndex(esIndex.split(":")[1]);
            Map<String, Object> map = ClassSugar.PropertyDescriptors.toNameValueMap(converted);
            map.remove("esIndex");
            map.put(key, val);
            super.output(map, level);
        } else {
            converted.setEsIndex(ESLogRecordItem.formatIndex(esIndex));
            super.output(converted);
        }
    }

    public ESLogRecordItem convert(LogRecordItem logRecordItem) {
        String ip = LogSingle.getLocalIp();
        String esIndex = properties.getEsIndex();
        if (!StringUtils.hasText(esIndex))
            log.warn(new IllegalArgumentException("es-index is empty."));
        else
            esIndex = esIndex.toLowerCase();
        return new ESLogRecordItem(logRecordItem, esIndex, ip);
    }
}
