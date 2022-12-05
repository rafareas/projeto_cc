package parsingDataBase;

import java.util.ArrayList;

public class Proto {
    private String msgID;
    private String flags;
    private String responseCode;
    private String N_values;
    private String N_authorities;
    private String N_extraValue;
    private String query_info_name;
    private String query_info_type;
    private ArrayList<String> response_values;
    private ArrayList<String> authoraties_values;
    private ArrayList<String> extra_values;

    public Proto(String msgID, String flags, String responseCode,String n_values,String n_authorities,String n_extraValue,String query_info_name,String query_info_type,
                 ArrayList<String> response_values,ArrayList<String> authoraties_values, ArrayList<String> extra_values) {
        this.msgID = msgID;
        this.flags = flags;
        this.responseCode = responseCode;
        this.responseCode = responseCode;
        this.N_values = n_values;
        this.N_authorities = n_authorities;
        this.N_extraValue = n_extraValue;
        this.query_info_name = query_info_name;
        this.query_info_type = query_info_type;
        this.response_values = response_values;
        this.authoraties_values = authoraties_values;
        this.extra_values = extra_values;
    }

    /*
    public Proto(Proto obj){
        this.msgID = obj.getMsgIp();
        this.flags = obj.getFlags();
        this.responseCode = obj.getResponseCode();
        this.N_values = obj.getN_values();
        this.N_authorities = obj.getN_authorities();
        this.N_extraValue = obj.getN_extraValue();
        this.query_info_name = obj.getQuery_info_name();
        this.query_info_type = obj.getQuery_info_name();
        this.response_values = obj.getResponse_values();
        this.authoraties_values = obj.getAuthoraties_values();
        this.extra_values = obj.getExtra_values();
    }

     */

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getN_values() {
        return N_values;
    }

    public void setN_values(String n_values) {
        N_values = n_values;
    }

    public String getN_authorities() {
        return N_authorities;
    }

    public void setN_authorities(String n_authorities) {
        N_authorities = n_authorities;
    }

    public String getN_extraValue() {
        return N_extraValue;
    }

    public void setN_extraValue(String n_extraValue) {
        N_extraValue = n_extraValue;
    }

    public String getQuery_info_name() {
        return query_info_name;
    }

    public void setQuery_info_name(String query_info_name) {
        this.query_info_name = query_info_name;
    }

    public String getQuery_info_type() {
        return query_info_type;
    }

    public void setQuery_info_type(String query_info_type) {
        this.query_info_type = query_info_type;
    }

    public ArrayList<String> getResponse_values() {
        return response_values;
    }

    public void setResponse_values(ArrayList<String> response_values) {
        this.response_values = response_values;
    }

    public ArrayList<String> getAuthoraties_values() {
        return authoraties_values;
    }

    public void setAuthoraties_values(ArrayList<String> authoraties_values) {
        this.authoraties_values = authoraties_values;
    }

    public ArrayList<String> getExtra_values() {
        return extra_values;
    }

    public void setExtra_values(ArrayList<String> extra_values) {
        this.extra_values = extra_values;
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


    @Override
    public String toString() {
        return "" + this.msgID + "," + this.flags + "," + this.responseCode + "," + this.N_values + "," +
                this.N_authorities + "," + this.N_extraValue + ";" + this.query_info_name + "," + this.query_info_type + ";" +
                this.response_values + "," + this.authoraties_values + "," + this.extra_values;
    }
}
