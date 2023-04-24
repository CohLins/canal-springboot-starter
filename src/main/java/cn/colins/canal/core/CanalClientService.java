package cn.colins.canal.core;


import cn.colins.canal.entity.CanalData;
import cn.colins.canal.entity.CanalInstanceInfo;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CanalClientService implements Runnable {

    private final static Logger log= LoggerFactory.getLogger(CanalClientService.class);

    private CanalConnector canalConnector;

    private CanalInstanceInfo instanceInfo;

    private String serverKey;

    private int retryNum=0;

    private int maxRetryNum=5;

    private int emptyCount;


    public CanalClientService(CanalConnector canalConnector, CanalInstanceInfo instanceInfo, String serverKey) {
        this.canalConnector = canalConnector;
        this.instanceInfo = instanceInfo;
        this.serverKey = serverKey;
    }

    public void start() {
        new Thread(this).start();
    }


    @Override
    public void run() {
        try{
            connect();
            while (true) {
                // 尝试从master那边拉去数据batchSize条记录
                Message message = canalConnector.getWithoutAck(instanceInfo.getBatchSize(),instanceInfo.getGetMsgTimeout(), TimeUnit.MILLISECONDS);
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    // 如果连续10次拉取数据都为空则睡眠1s
                    dataEmptyHandler();
                } else {
                    dataHandle(message.getEntries());
                }
                canalConnector.ack(batchId);
            }
        }catch (Exception e){
            if (retryNum < maxRetryNum){
                restart(e);
            }else {
                log.error(" canalServer:{} exception occurred：{}，{}",serverKey,e.getMessage(),e);
            }
        }finally {
            disconnect();
        }
    }

    /**
     * 数据处理
     *
     * @param entrys
     */
    private void dataHandle(List<CanalEntry.Entry> entrys) throws InvalidProtocolBufferException {
        for (CanalEntry.Entry entry : entrys) {
            if (CanalEntry.EntryType.ROWDATA == entry.getEntryType()) {
                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                CanalEntry.EventType eventType = rowChange.getEventType();
                List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
                for (CanalEntry.RowData rowData : rowDatasList) {
                    if (eventType == CanalEntry.EventType.DELETE) {
                        Map<String, Object> beforeData = getColumnData(rowData.getBeforeColumnsList());

                        // 数据处理
                        CanalDataHandler canalDataHandler = CanalFactory.CANAL_DATA_HANDLER.get(serverKey);
                        canalDataHandler.deleteHandler(new CanalData(entry.getHeader().getTableName(),beforeData,null));
                    } else if (eventType == CanalEntry.EventType.UPDATE) {
                        Map<String, Object> beforeData = getColumnData(rowData.getBeforeColumnsList());
                        Map<String, Object> afterData = getColumnData(rowData.getAfterColumnsList());

                        // 数据处理
                        CanalDataHandler canalDataHandler = CanalFactory.CANAL_DATA_HANDLER.get(serverKey);
                        canalDataHandler.updateHandler(new CanalData(entry.getHeader().getTableName(),beforeData,afterData));
                    } else if (eventType == CanalEntry.EventType.INSERT) {
                        Map<String, Object> afterData = getColumnData(rowData.getAfterColumnsList());

                        // 数据处理
                        CanalDataHandler canalDataHandler = CanalFactory.CANAL_DATA_HANDLER.get(serverKey);
                        canalDataHandler.insertHandler(new CanalData(entry.getHeader().getTableName(),null,afterData));
                    }
                }
            }
        }
    }


    private Map<String, Object> getColumnData(List<CanalEntry.Column> newColumnList){
        Map<String, Object> data = new HashMap<>();
        newColumnList.stream().forEach(item -> {
            if(StringUtils.isNotEmpty(item.getValue())){
                data.put(item.getName(), item.getValue());
            }
        });
        return data;
    }


    private void dataEmptyHandler(){
        emptyCount++;
        Thread.yield();
        if (emptyCount > 10) {
            emptyCount = 0;
            sleep(1000);
        }
    }


    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void disconnect(){
        if(canalConnector!=null){
            canalConnector.disconnect();
        }
        log.info("canalServer:{} has been disconnected",serverKey);
    }

    private void connect(){
        canalConnector.connect();
        log.info(" canalServer:{} connect success,start data handler ",serverKey);
        canalConnector.subscribe(instanceInfo.getSubscribe());
        // 回滚寻找上次中断的位置
        canalConnector.rollback();
    }

    private void restart(Exception e){
        retryNum++;
        log.warn("canalServer:{} exception occurred：{} start reconnect num:[}",serverKey,e.getMessage(),retryNum);
        sleep(3000);
        start();
    }
}
