package com.aio.portable.swiss.middleware.canal;

import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import java.util.function.Consumer;

public class CanalWatcher {
    private static final LocalLog log = LocalLog.getLog(CanalWatcher.class);

    private CanalConnector connector;
    private final Consumer<SqlLog> handler;
    private int batchSize = 1000;
    private int sleepMillis = 1000;
    private boolean toContinue = true;

    public void setConnector(CanalConnector connector) {
        this.connector = connector;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getSleepMillis() {
        return sleepMillis;
    }

    public void setSleepMillis(int sleepMillis) {
        this.sleepMillis = sleepMillis;
    }

    protected CanalWatcher(CanalConnector connector, Consumer<SqlLog> handler) {
        this.connector = connector;
        this.handler = handler;
    }

    public static void watch(CanalConnector connector, Consumer<SqlLog> handler) {
        new CanalWatcher(connector, handler).watch();
    }

    public void watch() {
        try {
            connector.connect();
            //订阅数据库表,全部表
            connector.subscribe(".*\\..*");
            //回滚到未进行ack的地方，下次fetch的时候，可以从最后一个没有ack的地方开始拿
            connector.rollback();

            while (toContinue) {
                Message message = connector.getWithoutAck(batchSize);
                //获取批量ID
                long batchId = message.getId();
                //获取批量的数量
                int size = message.getEntries().size();
                //如果没有数据
                if (batchId == -1 || size == 0) {
                    try {
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e) {
                        log.error("listen error", e);
                    }
                    continue;
                } else {
                    SqlLog sqlLog = SqlLog.convert(message.getEntries());
                    handler.accept(sqlLog);

//                    sqlLog.getTableModelMapping().put("tb_commodity_info", TableModel.class);
//                    List<EntryEntity> entryEntityList = sqlLog.toEntryEntityList();
//                    entryEntityList.forEach(c -> {
//                        c.getRowDataEntityList().forEach(d -> {
//                            final TableModel beforeRowModel = (TableModel) d.getBeforeRowModel();
//                            final TableModel afterRowModel = (TableModel) d.getAfterRowModel();
//                        });
//                    });
                    connector.ack(batchId);
                }
            }
        } catch (CanalClientException e) {
            log.error("listen error", e);
        } finally {
            connector.disconnect();
        }
    }
}
