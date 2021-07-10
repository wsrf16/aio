package com.aio.portable.park.unit.canal;

import com.aio.portable.swiss.middleware.canal.CanalWatcher;
import com.aio.portable.swiss.middleware.canal.EntryEntity;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.List;

@TestComponent
public class SqlLogTest implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("192.168.133.133", 11111), "example", "canal", "canal");
        new CanalWatcher(connector,
                (sqlLog) -> {
                    sqlLog.getTableModelMapping().put("tb_commodity_info", TableModel.class);
                    List<EntryEntity> entryEntityList = sqlLog.toEntryEntityList();
                    entryEntityList.forEach(c -> {
                        c.getRowDataEntityList().forEach(d -> {
                            final TableModel beforeRowModel = (TableModel) d.getBeforeRowModel();
                            final TableModel afterRowModel = (TableModel) d.getAfterRowModel();
                            c.getHeader().getEventType();

                            System.out.println();
                        });
                    });
                }).listen();
    }

}