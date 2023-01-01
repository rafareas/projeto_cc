package parsingDataBase;

import java.net.InetAddress;
import java.util.Objects;

public class ComLineInfo {
    private String msgId;
    private String flag;
    private int response_code;
    private int n_values;
    private int n_authoraties;
    private int n_extra_values;
    private String name;
    private String type;

    public ComLineInfo(String msgId, String flag, int response_code, int n_values, int n_authoraties, int n_extra_values,String name, String type) {
        this.flag = flag;
        this.msgId = msgId;
        this.response_code = response_code;
        this.n_values = n_values;
        this.n_authoraties = n_authoraties;
        this.n_extra_values = n_extra_values;
        this.name = name;
        this.type = type;
    }


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public int getN_values() {
        return n_values;
    }

    public void setN_values(int n_values) {
        this.n_values = n_values;
    }

    public int getN_authoraties() {
        return n_authoraties;
    }

    public void setN_authoraties(int n_authoraties) {
        this.n_authoraties = n_authoraties;
    }

    public int getN_extra_values() {
        return n_extra_values;
    }

    public void setN_extra_values(int n_extra_values) {
        this.n_extra_values = n_extra_values;
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
        return getResponse_code() == that.getResponse_code() && getN_values() == that.getN_values() && getN_authoraties() == that.getN_authoraties() && getN_extra_values() == that.getN_extra_values()  && Objects.equals(getMsgId(), that.getMsgId()) && Objects.equals(getFlag(), that.getFlag()) && Objects.equals(getName(), that.getName()) && Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash( getMsgId(), getFlag(), getResponse_code(), getN_values(), getN_authoraties(), getN_extra_values(), getName(), getType());
    }

    @Override
    public String toString() {
        return this.msgId +  "," + this.flag + "," + this.response_code + "," + this.n_values + "," + this.n_authoraties + "," + this.n_extra_values + ";" + this.name + "," + this.type + ";";
    }
}
