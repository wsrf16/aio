package com.aio.portable.park.unit.canal;

import com.aio.portable.swiss.middleware.canal.CanalWatcher;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetSocketAddress;

//@TestComponent
public class SqlLogTest implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("192.168.133.133", 11111), "example", "canal", "canal");
        CanalWatcher.watch(connector,
                sqlLog -> {
                    sqlLog.getTableModelMapping().put("tb_commodity_info", TableModel.class);
                    sqlLog.toEntryEntityList().forEach(c -> {
                        c.getRowDataEntityList().forEach(d -> {
                            TableModel beforeRowModel = (TableModel) d.getBeforeRowModel();
                            TableModel afterRowModel = (TableModel) d.getAfterRowModel();
                            CanalEntry.EventType eventType = c.getHeader().getEventType();
                            System.out.println();
                        });
                    });
                });
    }

}