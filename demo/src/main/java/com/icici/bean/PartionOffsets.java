package com.icici.bean;
 
public class PartionOffsets {
    private long lag;
    private long timestamp = System.currentTimeMillis();
    private long endOffset;
    private long currentOffset;
    private int partion;
    private String topic;
 
    public PartionOffsets(long endOffset, long currentOffset, int partion, String topic) {
        this.endOffset = endOffset;
        this.currentOffset = currentOffset;
        this.partion = partion;
        this.topic = topic;
        this.lag = endOffset - currentOffset;
    }
 
    public long getLag() {
        return lag;
    }
 
    public void setLag(long lag) {
        this.lag = lag;
    }
 
    public long getTimestamp() {
        return timestamp;
    }
 
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
 
    public long getEndOffset() {
        return endOffset;
    }
 
    public void setEndOffset(long endOffset) {
        this.endOffset = endOffset;
    }
 
    public long getCurrentOffset() {
        return currentOffset;
    }
 
    public void setCurrentOffset(long currentOffset) {
        this.currentOffset = currentOffset;
    }
 
    public int getPartion() {
        return partion;
    }
 
    public void setPartion(int partion) {
        this.partion = partion;
    }
 
    public String getTopic() {
        return topic;
    }
 
    public void setTopic(String topic) {
        this.topic = topic;
    }
 
    @Override
    public String toString() {
        return "PartionOffsets [lag=" + lag + ", timestamp=" + timestamp + ", endOffset=" + endOffset
                + ", currentOffset=" + currentOffset + ", partion=" + partion + ", topic=" + topic + "]";
    }
 
}
 