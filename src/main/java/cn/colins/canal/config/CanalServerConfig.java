package cn.colins.canal.config;


import cn.colins.canal.entity.CanalServerInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "canal.server")
public class CanalServerConfig {
    private boolean enable=false;
    private List<CanalServerInfo> serverInfos;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<CanalServerInfo> getServerInfos() {
        return serverInfos;
    }

    public void setServerInfos(List<CanalServerInfo> serverInfos) {
        this.serverInfos = serverInfos;
    }
}
