package parsingDataBase;

import java.net.InetAddress;
import java.util.Objects;

public class ComLineInfo {
    private InetAddress ip;
    private String msgId;
    private String name;
    private String type;

    public ComLineInfo(InetAddress ip,String msgId, String name, String type) {
        this.ip = ip;
        this.msgId = msgId;
        this.name = name;
        this.type = type;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComLineInfo that = (ComLineInfo) o;
        return Objects.equals(getIp(), that.getIp()) && Objects.equals(getMsgId(), that.getMsgId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIp(), getMsgId(), getName(), getType());
    }

    @Override
    public String toString() {
        return this.msgId +  ";" + this.ip + ";" + this.name + ";" + this.type;
    }
}
