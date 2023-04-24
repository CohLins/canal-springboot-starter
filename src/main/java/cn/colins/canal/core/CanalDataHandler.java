package cn.colins.canal.core;

import cn.colins.canal.entity.CanalData;

public interface CanalDataHandler {

     void insertHandler(CanalData data);

     void updateHandler(CanalData data);

     void deleteHandler(CanalData data);

     boolean errorHandler(CanalData data,Exception e);
}
