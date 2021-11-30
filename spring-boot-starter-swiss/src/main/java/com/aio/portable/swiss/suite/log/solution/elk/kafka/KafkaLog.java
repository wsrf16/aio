package com.aio.portable.swiss.suite.log.solution.elk.kafka;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.facade.Printer;
import com.aio.portable.swiss.suite.log.solution.elk.ESLogBean;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by York on 2017/11/23.
 */
public class KafkaLog extends LogSingle {
    private final static Log log = LogFactory.getLog(KafkaLog.class);
    protected KafkaLogProperties properties;

    public KafkaLogProperties getProperties() {
        return properties;
    }

    public KafkaLog setProperties(KafkaLogProperties properties) {
        this.properties = properties;
        return this;
    }



    public KafkaLog(String name) {
        super(name);
    }

    public KafkaLog(Class<?> clazz) {
        this(clazz.toString());
    }

    public KafkaLog() {
        this(StackTraceSugar.Previous.getClassName());
    }



    @Override
    protected void initialPrinter() {
        properties = KafkaLogProperties.singletonInstance();
        printer = KafkaPrinter.instance(this.getName(), properties);
    }

    @Override
    protected void output(Printer printer, LogBean logBean) {
        ESLogBean converted = convert(logBean);
        LevelEnum level = converted.getLevel();
        String esIndex = converted.getEsIndex();
        if (esIndex == null) {
            throw new NullPointerException("esIndex");
        } else if (esIndex.contains(":")) {
            String key = esIndex.split(":")[0];
            String val = ESLogBean.formatIndex(esIndex.split(":")[1]);
            Map<String, Object> map = BeanSugar.PropertyDescriptors.toNameValueMap(converted);
            map.remove("esIndex");
            map.put(key, val);
            super.output(printer, map, level);
        } else {
            converted.setEsIndex(ESLogBean.formatIndex(esIndex));
            super.output(printer, converted);
        }
    }

    public ESLogBean convert(LogBean logBean) {
        String ip = LogSingle.getLocalIp();
        String esIndex = properties.getEsIndex();
        if (StringUtils.isEmpty(esIndex))
            log.warn(new IllegalArgumentException("es-index is empty."));
        return new ESLogBean(logBean, esIndex, ip);
    }
}
