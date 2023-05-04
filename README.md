一个针对canal开箱即用的客户端，关于canal有关的环境准备本文不做说明

可以参考这个：[环境的准备](https://blog.csdn.net/weixin_44102992/article/details/120454307)

## 简介

canal本身分两种方式部署：单机和集群，而两者都可以同时监听多个Mysql的变化，架构如下：

单机模式

![输入图片说明](https://foruda.gitee.com/images/1683172103450201627/4edae422_6577380.png "屏幕截图")

集群模式

![输入图片说明](https://foruda.gitee.com/images/1683172132090014137/88125551_6577380.png "屏幕截图")

以上不管是集群模式还是单机模式，在此项目中都被称为一个CanalServer，为了防止需要同时连接多个单机或者多个集群这种场景出现，本项目支持同时连接多个，但是需要给每个CanalServer打上一个唯一的别名

## 使用说明

### 配置文件

- CanalServer可以配置多个，但是一定要有唯一的alias
- 集群的话地址用","隔开
- 如果集群是连接的ZK，还需要配置hasUseZk: true

```Java
canal:
  server:
    # 如果不想启用这个就设置成false 或者不配
    enable: true
    server-infos:
      # 如果是单机
      - alias: canalServer1
        address: IP:PORT
        userName: userName
        password: password
      # 如果是集群
      - alias: canalServer2
        address: IP:PORT,IP:PORT,IP:PORT
        userName: userName
        password: password
      # 如果是集群并且是连接zk
      - alias: canalServer3
        address: IP:PORT,IP:PORT,IP:PORT
        hasUseZk: true
        userName: userName
        password: password
```

### 处理类

处理类只需要实现一个接口并打上一个注解就可以了，先看示例

```Java
@CanalServerAnnotation(
        alias = "canalServer1",
        instances = {@CanalInstanceAnnotation(
                destination = "example",
                subscribe = "test.user"
        )}
)
public class CanalTestHandler1 implements CanalDataHandler {
    private static final Logger log = LoggerFactory.getLogger(CanalTestHandler1.class);

    public void insertHandler(CanalData data) {
        log.info("insert data : {}", JSON.toJSON(data));
    }

    public void updateHandler(CanalData data) {
        log.info("update data : {}", JSON.toJSON(data));
    }

    public void deleteHandler(CanalData data) {
        log.info("delete data : {}", JSON.toJSON(data));
    }

    public boolean errorHandler(CanalData data, Exception e) {
        log.info("error data : {}", JSON.toJSON(data));
        return false;
    }
}
```

#### 注解说明

@CanalServerAnnotation：
  - alias： 要与配置文件中CanalServer的alias一致，代表是该服务下的监听类
  - instances： 实例配置，是一个数组，因为一个CanalServer可以有多个实例

@CanalInstanceAnnotation：
  - destination：实例的名称（也就是canal服务端下那个文件夹的名称）
  - subscribe：监听表的表达式（默认监听全部）
  - batchSize：一次拉取多少条消息（默认1000）
  - getMsgTimeout：拉取消息等待时长 单位：毫秒（默认1s拉一次）

#### 接口说明

```Java
public interface CanalDataHandler {
    // 插入数据的监听处理
    void insertHandler(CanalData data);
    // 更新数据的监听处理
    void updateHandler(CanalData data);
    // 删除数据的监听处理
    void deleteHandler(CanalData data);
    // 以上三个如果发生异常则会执行这个 异常处理  
    // 返回 true：代表这批消息会回滚  返回false：代表这批消息不回滚
    boolean errorHandler(CanalData data, Exception e);
}

```

CanalData数据实体类:

```Java
public class CanalData {
    // 表名
    private String tableName;
    // 变更前的数据
    private Map<String, Object> beforeData;
    // 变更后的数据
    private Map<String, Object> afterData;
}
canal监听的是binlog，所以：
// 如果是插入则只有afterData
// 如果是删除则只有beforeData
// 如果是更新则beforeData代表更新前的数据，afterData代表更新后的数据
Map<String, Object> :
// key代表字段名
// object代表数据值
```



## 效果

![输入图片说明](https://foruda.gitee.com/images/1683172155311180906/084a043f_6577380.png "屏幕截图")
