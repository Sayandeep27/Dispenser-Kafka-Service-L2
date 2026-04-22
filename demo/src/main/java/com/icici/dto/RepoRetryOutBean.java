package com.icici.dto;
 
public class RepoRetryOutBean {
    
    private String DEPT_ID;
    private String APP_ID;
    private String CHANNEL;
    private String VENDOR_CD;
    private String TOPIC_NAME;
    private String TSG_DEATILS;
    
    public RepoRetryOutBean() {
        super();
    }
    public RepoRetryOutBean(String dEPT_ID, String aPP_ID, String cHANNEL, String vENDOR_CD, String tOPIC_NAME,
            String tSG_DEATILS) {
        super();
        DEPT_ID = dEPT_ID;
        APP_ID = aPP_ID;
        CHANNEL = cHANNEL;
        VENDOR_CD = vENDOR_CD;
        TOPIC_NAME = tOPIC_NAME;
        TSG_DEATILS = tSG_DEATILS;
    }
    @Override
    public String toString() {
        return "RepoRetryOutBean [DEPT_ID=" + DEPT_ID + ", APP_ID=" + APP_ID + ", CHANNEL=" + CHANNEL + ", VENDOR_CD="
                + VENDOR_CD + ", TOPIC_NAME=" + TOPIC_NAME + ", TSG_DEATILS=" + TSG_DEATILS + "]";
    }
    public String getDEPT_ID() {
        return DEPT_ID;
    }
    public void setDEPT_ID(String dEPT_ID) {
        DEPT_ID = dEPT_ID;
    }
    public String getAPP_ID() {
        return APP_ID;
    }
    public void setAPP_ID(String aPP_ID) {
        APP_ID = aPP_ID;
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
    public String getTOPIC_NAME() {
        return TOPIC_NAME;
    }
    public void setTOPIC_NAME(String tOPIC_NAME) {
        TOPIC_NAME = tOPIC_NAME;
    }
    public String getTSG_DEATILS() {
        return TSG_DEATILS;
    }
    public void setTSG_DEATILS(String tSG_DEATILS) {
        TSG_DEATILS = tSG_DEATILS;
    }
    
 
}
 
 