package cn.colins.canal.entity;

import java.util.List;
import java.util.Set;


public class CanalServerInfo {
    private String alias;
    private String address;
    private boolean hasUseZk = false;
    private Set<CanalInstanceInfo> instanceInfos;
    private String userName;
    private String password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public boolean isHasUseZk() {
        return hasUseZk;
    }

    public void setHasUseZk(boolean hasUseZk) {
        this.hasUseZk = hasUseZk;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Set<CanalInstanceInfo> getInstanceInfos() {
        return instanceInfos;
    }

    public void setInstanceInfos(Set<CanalInstanceInfo> instanceInfos) {
        this.instanceInfos = instanceInfos;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alias == null) ? 0 : alias.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CanalServerInfo other = (CanalServerInfo) obj;
        if (alias == null) {
            if (other.alias != null)
                return false;
        } else if (!alias.equals(other.alias))
            return false;
        return true;
    }
}
