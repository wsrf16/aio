package com.aio.portable.swiss.suite.log.solution.elk.rabbit;

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
public class RabbitMQLog extends LogSingle {
    private final static Log log = LogFactory.getLog(RabbitMQLog.class);

    protected RabbitMQLogProperties properties;

    public RabbitMQLogProperties getProperties() {
        return properties;
    }

    public RabbitMQLog setProperties(RabbitMQLogProperties properties) {
        this.properties = properties;
        return this;
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
        properties = RabbitMQLogProperties.singletonInstance();
        printer = RabbitMQPrinter.instance(this.getName(), properties);
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
        if (!StringUtils.hasText(esIndex))
            log.warn(new IllegalArgumentException("es-index is empty."));
        return new ESLogBean(logBean, esIndex, ip);
    }
}
