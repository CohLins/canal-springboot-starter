package cn.colins.canal.core;

import cn.colins.canal.entity.CanalData;

public interface CanalDataHandler {

    default void insertHandler(CanalData data){

    }

    default void updateHandler(CanalData data){

    }

    default void deleteHandler(CanalData data){

    }

    default boolean errorHandler(CanalData data,Exception e){
        return true;
    }
}
