package cn.colins.canal.entity;


public class CanalInstanceInfo {
    private String alias;
    private String destination;
    private String subscribe;
    private int batchSize;
    private long getMsgTimeout;

    public CanalInstanceInfo(String alias, String destination, String subscribe, int batchSize, long getMsgTimeout) {
        this.alias = alias;
        this.destination = destination;
        this.subscribe = subscribe;
        this.batchSize = batchSize;
        this.getMsgTimeout = getMsgTimeout;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public long getGetMsgTimeout() {
        return getMsgTimeout;
    }

    public void setGetMsgTimeout(long getMsgTimeout) {
        this.getMsgTimeout = getMsgTimeout;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alias == null) ? 0 : alias.hashCode());
        result = prime * result + ((destination == null) ? 0 : destination.hashCode());
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
        CanalInstanceInfo other = (CanalInstanceInfo) obj;
        if (alias == null) {
            if (other.alias != null)
                return false;
        } else if (!alias.equals(other.alias))
            return false;
        if (destination == null) {
            if (other.destination != null)
                return false;
        } else if (!destination.equals(other.destination))
            return false;
        return true;
    }
}
