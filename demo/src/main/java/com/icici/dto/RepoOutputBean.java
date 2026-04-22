package com.icici.dto;
 
public class RepoOutputBean {
    
    private String DEPTID;
    private String APPID;
    private String CHANNEL;
    private String VENDOR_CD;
    private String SENTCOUNT;
    private String TOPIC_NAME;
    private String POPMAILID;
    private String POPSENDERID;
    private String TSG_DEATILS;
    private String PIN;
    
    public RepoOutputBean() {
        super();
    }
    
    public RepoOutputBean(String dEPTID, String aPPID, String cHANNEL, String vENDOR_CD, String sENTCOUNT,
            String tOPIC_NAME, String pOPMAILID, String pOPSENDERID) {
        super();
        DEPTID = dEPTID;
        APPID = aPPID;
        CHANNEL = cHANNEL;
        VENDOR_CD = vENDOR_CD;
        SENTCOUNT = sENTCOUNT;
        TOPIC_NAME = tOPIC_NAME;
        POPMAILID = pOPMAILID;
        POPSENDERID = pOPSENDERID;
//      TSG_DEATILS = tSG_DEATILS;
//      PIN = pIN;
    }
    
    public RepoOutputBean(String dEPTID, String aPPID, String cHANNEL, String vENDOR_CD, String sENTCOUNT,
            String tOPIC_NAME, String pOPMAILID, String pOPSENDERID, String tSG_DEATILS, String pIN) {
        super();
        DEPTID = dEPTID;
        APPID = aPPID;
        CHANNEL = cHANNEL;
        VENDOR_CD = vENDOR_CD;
        SENTCOUNT = sENTCOUNT;
        TOPIC_NAME = tOPIC_NAME;
        POPMAILID = pOPMAILID;
        POPSENDERID = pOPSENDERID;
        TSG_DEATILS = tSG_DEATILS;
        PIN = pIN;
    }
    @Override
    public String toString() {
        return "RepoOutputBean [DEPTID=" + DEPTID + ", APPID=" + APPID + ", CHANNEL=" + CHANNEL + ", VENDOR_CD="
                + VENDOR_CD + ", SENTCOUNT=" + SENTCOUNT + ", TOPIC_NAME=" + TOPIC_NAME + ", POPMAILID=" + POPMAILID
                + ", POPSENDERID=" + POPSENDERID + ", TSG_DEATILS=" + TSG_DEATILS + ", PIN=" + PIN + "]";
    }
    public String getDEPTID() {
        return DEPTID;
    }
    public void setDEPTID(String dEPTID) {
        DEPTID = dEPTID;
    }
    public String getAPPID() {
        return APPID;
    }
    public void setAPPID(String aPPID) {
        APPID = aPPID;
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
    public String getSENTCOUNT() {
        return SENTCOUNT;
    }
    public void setSENTCOUNT(String sENTCOUNT) {
        SENTCOUNT = sENTCOUNT;
    }
    public String getTOPIC_NAME() {
        return TOPIC_NAME;
    }
    public void setTOPIC_NAME(String tOPIC_NAME) {
        TOPIC_NAME = tOPIC_NAME;
    }
    public String getPOPMAILID() {
        return POPMAILID;
    }
    public void setPOPMAILID(String pOPMAILID) {
        POPMAILID = pOPMAILID;
    }
    public String getPOPSENDERID() {
        return POPSENDERID;
    }
    public void setPOPSENDERID(String pOPSENDERID) {
        POPSENDERID = pOPSENDERID;
    }
    public String getTSG_DEATILS() {
        return TSG_DEATILS;
    }
    public void setTSG_DEATILS(String tSG_DEATILS) {
        TSG_DEATILS = tSG_DEATILS;
    }
    public String getPIN() {
        return PIN;
    }
    public void setPIN(String pIN) {
        PIN = pIN;
    }
 
}
 