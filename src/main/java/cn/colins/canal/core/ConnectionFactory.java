package cn.colins.canal.core;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.client.impl.ClusterCanalConnector;
import com.alibaba.otter.canal.client.impl.ClusterNodeAccessStrategy;
import com.alibaba.otter.canal.common.zookeeper.ZkClientx;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;


public class ConnectionFactory {
//    public static CanalConnector create(CanalClientConfig canalInfoConfig, String destination){
//        if(!StringUtil.isNullOrEmpty(canalInfoConfig.getHosts())){
//            List<InetSocketAddress> inetSocketAddressList=new ArrayList<>();
//            String[] hosts=canalInfoConfig.getHosts().split(",");
//            for (String hostInfo:
//            hosts) {
//               String[] hostAndPort= hostInfo.split(":");
//                inetSocketAddressList.add(new InetSocketAddress(hostAndPort[0], Integer.valueOf(hostAndPort[1])));
//            }
//            return CanalConnectors.newClusterConnector(inetSocketAddressList,destination, canalInfoConfig.getUsername(),canalInfoConfig.getPassword());
//        }else{
//           return newClusterConnector(canalInfoConfig.getZkHosts(),destination, canalInfoConfig.getUsername(), canalInfoConfig.getPassword());
//        }
//
//
//    }
    /**
     * 创建带cluster模式的客户端链接，自动完成failover切换，服务器列表自动扫描
     *
     * @param zkServers
     * @param destination
     * @param username
     * @param password
     * @return
     */
    public static CanalConnector newClusterConnector(String zkServers, String destination, String username,
                                                     String password) {
        ClusterCanalConnector canalConnector = new ClusterCanalConnector(username,
                password,
                destination,
                new ClusterNodeAccessStrategy(destination, ZkClientx.getZkClient(zkServers)));
        canalConnector.setSoTimeout(60 * 1000);
        canalConnector.setIdleTimeout(60 * 60 * 1000);
        return canalConnector;
    }
}
