package com.icici.dto;
 
import com.fasterxml.jackson.annotation.JsonProperty;
 
public class KafkaFailBean {
    
    
    @JsonProperty("msg_id")
    private String MSGID;
    @JsonProperty("dept_id")
    private String DEPT;
    @JsonProperty("app_id")
    private String APPID;
    @JsonProperty("mobile_no")
    private String MOBILE;
 
    @JsonProperty("pin")
    private String PIN;
    @JsonProperty("pop_mail_id")
    private String POP_MAIL_ID;
    @JsonProperty("pop_sender_addr")
    private String POP_SENDER_ADD;
    @JsonProperty("tsg_details")
    private String TSG_DETAIL;
    @JsonProperty("channel")
    private String CHANNEL;
    @JsonProperty("last_vendor_cd")
    private String VENDOR_CD;
    @JsonProperty("msg_text")
    private String MESSAGE;
    @JsonProperty("send_count")
    private String SENDCOUNT;
    @JsonProperty("topic_name")
    private String TOIPC;
    
    
    public KafkaFailBean() {
        super();
    }
    public KafkaFailBean(String mSGID, String dEPT, String aPPID, String mOBILE, String pIN, String pOP_MAIL_ID,
            String pOP_SENDER_ADD, String tSG_DETAIL, String cHANNEL, String vENDOR_CD, String mESSAGE,
            String sENDCOUNT, String tOIPC) {
        super();
        MSGID = mSGID;
        DEPT = dEPT;
        APPID = aPPID;
        MOBILE = mOBILE;
        PIN = pIN;
        POP_MAIL_ID = pOP_MAIL_ID;
        POP_SENDER_ADD = pOP_SENDER_ADD;
        TSG_DETAIL = tSG_DETAIL;
        CHANNEL = cHANNEL;
        VENDOR_CD = vENDOR_CD;
        MESSAGE = mESSAGE;
        SENDCOUNT = sENDCOUNT;
        TOIPC = tOIPC;
    }
    public String getMSGID() {
        return MSGID;
    }
    public void setMSGID(String mSGID) {
        MSGID = mSGID;
    }
    public String getDEPT() {
        return DEPT;
    }
    public void setDEPT(String dEPT) {
        DEPT = dEPT;
    }
    public String getAPPID() {
        return APPID;
    }
    public void setAPPID(String aPPID) {
        APPID = aPPID;
    }
    public String getMOBILE() {
        return MOBILE;
    }
    public void setMOBILE(String mOBILE) {
        MOBILE = mOBILE;
    }
    public String getPIN() {
        return PIN;
    }
    public void setPIN(String pIN) {
        PIN = pIN;
    }
    public String getPOP_MAIL_ID() {
        return POP_MAIL_ID;
    }
    public void setPOP_MAIL_ID(String pOP_MAIL_ID) {
        POP_MAIL_ID = pOP_MAIL_ID;
    }
    public String getPOP_SENDER_ADD() {
        return POP_SENDER_ADD;
    }
    public void setPOP_SENDER_ADD(String pOP_SENDER_ADD) {
        POP_SENDER_ADD = pOP_SENDER_ADD;
    }
    public String getTSG_DETAIL() {
        return TSG_DETAIL;
    }
    public void setTSG_DETAIL(String tSG_DETAIL) {
        TSG_DETAIL = tSG_DETAIL;
    }
    public String getCHANNEL() {
        return CHANNEL;
    }
    public void setCHANNEL(String cHANNEL) {
        CHANNEL = cHANNEL;
    }
    public String getVENDOR_CD() {
        return VENDOR_CD;
    }
    public void setVENDOR_CD(String vENDOR_CD) {
        VENDOR_CD = vENDOR_CD;
    }
    public String getMESSAGE() {
        return MESSAGE;
    }
    public void setMESSAGE(String mESSAGE) {
        MESSAGE = mESSAGE;
    }
    public String getSENDCOUNT() {
        return SENDCOUNT;
    }
    public void setSENDCOUNT(String sENDCOUNT) {
        SENDCOUNT = sENDCOUNT;
    }
    public String getTOIPC() {
        return TOIPC;
    }
    public void setTOIPC(String tOIPC) {
        TOIPC = tOIPC;
    }
    @Override
    public String toString() {
        return "KafkaFailBean [MSGID=" + MSGID + ", DEPT=" + DEPT + ", APPID=" + APPID + ", MOBILE=" + MOBILE + ", PIN="
                + PIN + ", POP_MAIL_ID=" + POP_MAIL_ID + ", POP_SENDER_ADD=" + POP_SENDER_ADD + ", TSG_DETAIL="
                + TSG_DETAIL + ", CHANNEL=" + CHANNEL + ", VENDOR_CD=" + VENDOR_CD + ", MESSAGE=" + MESSAGE
                + ", SENDCOUNT=" + SENDCOUNT + ", TOIPC=" + TOIPC + "]";
    }
    
 
}
 
 