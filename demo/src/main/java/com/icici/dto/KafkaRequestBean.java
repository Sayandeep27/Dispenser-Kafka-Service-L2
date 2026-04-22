package com.icici.dto;
 
import com.fasterxml.jackson.annotation.JsonProperty;
 
public class KafkaRequestBean {
    
    @JsonProperty("msg_id")
    private String MSGID;
    @JsonProperty("dept")
    private String DEPT;
    @JsonProperty("appid")
    private String APPID;
    @JsonProperty("mobile")
    private String MOBILE;
    @JsonProperty("deptmsgid")
    private String DEPTMSGID;
    @JsonProperty("message")
    private String MESSAGE;
    @JsonProperty("fromdatetime")
    private String FROMDATETIME;
    @JsonProperty("todatetime")
    private String TODATETIME;
    @JsonProperty("nodeliverytimefrom")
    private String NODELIVERYTIMEFROM;
    @JsonProperty("nodeliverytimeto")
    private String NODELIVERYTIMETO;
    @JsonProperty("httpmode")
    private String HTTPMODE;
    @JsonProperty("remarks")
    private String REMARK;
    @JsonProperty("trn__generate_timestamp")
    private String TRN_GENERATE_TIMESTAMP;
    @JsonProperty("duplicate_check")
    private String DUPLICATE_CHECK;
    @JsonProperty("remarks1")
    private String REMARKS1;
    @JsonProperty("remarks2")
    private String REMARKS2;
//  private String MSGID;
    @JsonProperty("topic_name")
    private String TOIPC;
    
    
    public KafkaRequestBean() {
    super();
}
    public KafkaRequestBean(String mSGID, String dEPT, String aPPID, String mOBILE, String dEPTMSGID, String mESSAGE,
        String fROMDATETIME, String tODATETIME, String nODELIVERYTIMEFROM, String nODELIVERYTIMETO, String hTTPMODE,
        String rEMARK, String tRN_GENERATE_TIMESTAMP, String dUPLICATE_CHECK, String rEMARKS1, String rEMARKS2,
        String tOIPC) {
    super();
    MSGID = mSGID;
    DEPT = dEPT;
    APPID = aPPID;
    MOBILE = mOBILE;
    DEPTMSGID = dEPTMSGID;
    MESSAGE = mESSAGE;
    FROMDATETIME = fROMDATETIME;
    TODATETIME = tODATETIME;
    NODELIVERYTIMEFROM = nODELIVERYTIMEFROM;
    NODELIVERYTIMETO = nODELIVERYTIMETO;
    HTTPMODE = hTTPMODE;
    REMARK = rEMARK;
    TRN_GENERATE_TIMESTAMP = tRN_GENERATE_TIMESTAMP;
    DUPLICATE_CHECK = dUPLICATE_CHECK;
    REMARKS1 = rEMARKS1;
    REMARKS2 = rEMARKS2;
    TOIPC = tOIPC;
}
    @Override
    public String toString() {
        return "KafkaRequestBean [MSGID=" + MSGID + ", DEPT=" + DEPT + ", APPID=" + APPID + ", MOBILE=" + MOBILE
                + ", DEPTMSGID=" + DEPTMSGID + ", MESSAGE=" + MESSAGE + ", FROMDATETIME=" + FROMDATETIME
                + ", TODATETIME=" + TODATETIME + ", NODELIVERYTIMEFROM=" + NODELIVERYTIMEFROM + ", NODELIVERYTIMETO="
                + NODELIVERYTIMETO + ", HTTPMODE=" + HTTPMODE + ", REMARK=" + REMARK + ", TRN_GENERATE_TIMESTAMP="
                + TRN_GENERATE_TIMESTAMP + ", DUPLICATE_CHECK=" + DUPLICATE_CHECK + ", REMARKS1=" + REMARKS1
                + ", REMARKS2=" + REMARKS2 + ", TOIPC=" + TOIPC + "]";
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
    public String getDEPTMSGID() {
        return DEPTMSGID;
    }
    public void setDEPTMSGID(String dEPTMSGID) {
        DEPTMSGID = dEPTMSGID;
    }
    public String getMESSAGE() {
        return MESSAGE;
    }
    public void setMESSAGE(String mESSAGE) {
        MESSAGE = mESSAGE;
    }
    public String getFROMDATETIME() {
        return FROMDATETIME;
    }
    public void setFROMDATETIME(String fROMDATETIME) {
        FROMDATETIME = fROMDATETIME;
    }
    public String getTODATETIME() {
        return TODATETIME;
    }
    public void setTODATETIME(String tODATETIME) {
        TODATETIME = tODATETIME;
    }
    public String getNODELIVERYTIMEFROM() {
        return NODELIVERYTIMEFROM;
    }
    public void setNODELIVERYTIMEFROM(String nODELIVERYTIMEFROM) {
        NODELIVERYTIMEFROM = nODELIVERYTIMEFROM;
    }
    public String getNODELIVERYTIMETO() {
        return NODELIVERYTIMETO;
    }
    public void setNODELIVERYTIMETO(String nODELIVERYTIMETO) {
        NODELIVERYTIMETO = nODELIVERYTIMETO;
    }
    public String getHTTPMODE() {
        return HTTPMODE;
    }
    public void setHTTPMODE(String hTTPMODE) {
        HTTPMODE = hTTPMODE;
    }
    public String getREMARK() {
        return REMARK;
    }
    public void setREMARK(String rEMARK) {
        REMARK = rEMARK;
    }
    public String getTRN_GENERATE_TIMESTAMP() {
        return TRN_GENERATE_TIMESTAMP;
    }
    public void setTRN_GENERATE_TIMESTAMP(String tRN_GENERATE_TIMESTAMP) {
        TRN_GENERATE_TIMESTAMP = tRN_GENERATE_TIMESTAMP;
    }
    public String getDUPLICATE_CHECK() {
        return DUPLICATE_CHECK;
    }
    public void setDUPLICATE_CHECK(String dUPLICATE_CHECK) {
        DUPLICATE_CHECK = dUPLICATE_CHECK;
    }
    public String getREMARKS1() {
        return REMARKS1;
    }
    public void setREMARKS1(String rEMARKS1) {
        REMARKS1 = rEMARKS1;
    }
    public String getREMARKS2() {
        return REMARKS2;
    }
    public void setREMARKS2(String rEMARKS2) {
        REMARKS2 = rEMARKS2;
    }
    public String getTOIPC() {
        return TOIPC;
    }
    public void setTOIPC(String tOIPC) {
        TOIPC = tOIPC;
    }
 
}
 
 