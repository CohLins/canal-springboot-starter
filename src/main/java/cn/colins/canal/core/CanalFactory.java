package cn.colins.canal.core;


import cn.colins.canal.entity.CanalServerInfo;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.util.Assert;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class CanalFactory implements SmartInitializingSingleton {

    private final static Logger log = LoggerFactory.getLogger(CanalFactory.class);

    public static ConcurrentHashMap<String, CanalServerInfo> CANAL_SERVER_CONFIG = new ConcurrentHashMap(8);
    public static ConcurrentHashMap<String, CanalDataHandler> CANAL_DATA_HANDLER = new ConcurrentHashMap(8);


    public static CanalConnector create(String address, boolean hasUseZk, String destination, String userName, String password) {
        if (hasUseZk) {
            return CanalConnectors.newClusterConnector(address, destination, userName, userName);
        } else {
            List<InetSocketAddress> inetSocketAddressList = new ArrayList<>(8);
            String[] hosts = address.split(",");
            for (String hostInfo : hosts) {
                String[] hostAndPort = hostInfo.split(":");
                Assert.isTrue(hostAndPort.length != 2, address + " Incorrect format");
                inetSocketAddressList.add(new InetSocketAddress(hostAndPort[0], Integer.valueOf(hostAndPort[1])));
            }
            return CanalConnectors.newClusterConnector(inetSocketAddressList, destination, userName, password);
        }
    }


    public static void registerDataHandler(String serverAlias, String instance, CanalDataHandler canalDataHandler) {
        CANAL_DATA_HANDLER.put(getCanalServerKey(serverAlias, instance), canalDataHandler);
    }

    private static String getCanalServerKey(String serverAlias, String instance) {
        return serverAlias + ":" + instance;
    }

    @Override
    public void afterSingletonsInstantiated() {

    }
}
