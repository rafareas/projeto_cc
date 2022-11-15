package comunication;

public class MyAppProto {
    private String msgID;
    private String flags;
    private String number;

    public MyAppProto(String msgID, String flags, String number) {
        this.msgID = msgID;
        this.flags = flags;
        this.number = number;
    }

    public String getMsgIp() {
        return msgID;
    }

    public void setMsgIp(String msgIp) {
        this.msgID = msgIp;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "" + this.msgID + ";" + this.flags + ";" + this.number;
    }
}
