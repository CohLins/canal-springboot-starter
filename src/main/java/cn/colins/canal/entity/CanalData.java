package cn.colins.canal.entity;

import java.util.Map;


public class CanalData {
    private String tableName;
    private Map<String, Object> beforeData;
    private Map<String, Object> afterData;

    public CanalData(String tableName, Map<String, Object> beforeData, Map<String, Object> afterData) {
        this.tableName = tableName;
        this.beforeData = beforeData;
        this.afterData = afterData;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public Map<String, Object> getBeforeData() {
        return beforeData;
    }

    public void setBeforeData(Map<String, Object> beforeData) {
        this.beforeData = beforeData;
    }

    public Map<String, Object> getAfterData() {
        return afterData;
    }

    public void setAfterData(Map<String, Object> afterData) {
        this.afterData = afterData;
    }
}
