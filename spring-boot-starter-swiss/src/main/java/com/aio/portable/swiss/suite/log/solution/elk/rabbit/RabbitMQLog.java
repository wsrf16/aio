package com.aio.portable.swiss.suite.log.solution.elk.rabbit;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.solution.elk.ESLogRecord;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogRecord;
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
    protected void initialPrinter() {
        refreshPrinter(null);
    }

    public void refreshPrinter(RabbitMQLogProperties properties) {
        this.properties =  properties == null ? RabbitMQLogProperties.getSingleton() : properties;
        this.printer = RabbitMQPrinter.instance(this.getName(), this.properties);
    }

    @Override
    protected void output(LogRecord logRecord) {
        ESLogRecord converted = convert(logRecord);
        LevelEnum level = converted.getLevel();
        String esIndex = converted.getEsIndex();
        if (esIndex == null) {
            throw new NullPointerException("esIndex");
        } else if (esIndex.contains(":")) {
            String key = esIndex.split(":")[0];
            String val = ESLogRecord.formatIndex(esIndex.split(":")[1]);
            Map<String, Object> map = BeanSugar.PropertyDescriptors.toNameValueMap(converted);
            map.remove("esIndex");
            map.put(key, val);
            super.output(map, level);
        } else {
            converted.setEsIndex(ESLogRecord.formatIndex(esIndex));
            super.output(converted);
        }
    }

    public ESLogRecord convert(LogRecord logRecord) {
        String ip = LogSingle.getLocalIp();
        String esIndex = properties.getEsIndex();
        if (!StringUtils.hasText(esIndex))
            log.warn(new IllegalArgumentException("es-index is empty."));
        return new ESLogRecord(logRecord, esIndex, ip);
    }
}
