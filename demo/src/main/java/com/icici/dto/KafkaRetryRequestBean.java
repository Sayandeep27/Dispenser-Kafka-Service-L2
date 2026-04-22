package com.icici.dto;
 
import com.fasterxml.jackson.annotation.JsonProperty;
 
public class KafkaRetryRequestBean {
    
    @JsonProperty("msg_id")
    private String sMsgId;
    @JsonProperty("dept_id")
    private String sDepId;
    @JsonProperty("app_id")
    private String sAppId;
    @JsonProperty("mobile_no")
    private String sMobileNo;
    @JsonProperty("msg_text")
    private String sMsgText;
    @JsonProperty("pin")
    private String sPin;
    @JsonProperty("pop_mail_id")
    private String sPopMailId;
    @JsonProperty("pop_sender_addr")
    private String sPopSenderAdd;
    @JsonProperty("tsg_details")
    private String stsgDetails;
    @JsonProperty("channel")
    private String sChannel;
    @JsonProperty("vendor_cd")
    private String sVendorCd;
    @JsonProperty("send_count")
    private String sSenderCount;
    @JsonProperty("topic_name")
    private String topicName;
    @JsonProperty("fromdatetime")
    private String fromdatetime;
    @JsonProperty("todatetime")
    private String todatetime;
    @JsonProperty("nodeliverytimefrom")
    private String nodeliverytimefrom;
    @JsonProperty("nodeliverytimeto")
    private String nodeliverytimeto;
    
    public String getsMsgId() {
        return sMsgId;
    }
    public void setsMsgId(String sMsgId) {
        this.sMsgId = sMsgId;
    }
    public String getsDepId() {
        return sDepId;
    }
    public void setsDepId(String sDepId) {
        this.sDepId = sDepId;
    }
    public String getsAppId() {
        return sAppId;
    }
    public void setsAppId(String sAppId) {
        this.sAppId = sAppId;
    }
    public String getsMobileNo() {
        return sMobileNo;
    }
    public void setsMobileNo(String sMobileNo) {
        this.sMobileNo = sMobileNo;
    }
    public String getsPin() {
        return sPin;
    }
    public void setsPin(String sPin) {
        this.sPin = sPin;
    }
    public String getsPopMailId() {
        return sPopMailId;
    }
    public void setsPopMailId(String sPopMailId) {
        this.sPopMailId = sPopMailId;
    }
    public String getsPopSenderAdd() {
        return sPopSenderAdd;
    }
    public void setsPopSenderAdd(String sPopSenderAdd) {
        this.sPopSenderAdd = sPopSenderAdd;
    }
    public String getStsgDetails() {
        return stsgDetails;
    }
    public void setStsgDetails(String stsgDetails) {
        this.stsgDetails = stsgDetails;
    }
    public String getsChannel() {
        return sChannel;
    }
    public void setsChannel(String sChannel) {
        this.sChannel = sChannel;
    }
    public String getsVendorCd() {
        return sVendorCd;
    }
    public void setsVendorCd(String sVendorCd) {
        this.sVendorCd = sVendorCd;
    }
    public String getsMsgText() {
        return sMsgText;
    }
    public void setsMsgText(String sMsgText) {
        this.sMsgText = sMsgText;
    }
    public String getsSenderCount() {
        return sSenderCount;
    }
    public void setsSenderCount(String sSenderCount) {
        this.sSenderCount = sSenderCount;
    }
    public String getTopicName() {
        return topicName;
    }
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    
    public String getFromdatetime() {
        return fromdatetime;
    }
    public void setFromdatetime(String fromdatetime) {
        this.fromdatetime = fromdatetime;
    }
    public String getTodatetime() {
        return todatetime;
    }
    public void setTodatetime(String todatetime) {
        this.todatetime = todatetime;
    }
    public String getNodeliverytimefrom() {
        return nodeliverytimefrom;
    }
    public void setNodeliverytimefrom(String nodeliverytimefrom) {
        this.nodeliverytimefrom = nodeliverytimefrom;
    }
    public String getNodeliverytimeto() {
        return nodeliverytimeto;
    }
    public void setNodeliverytimeto(String nodeliverytimeto) {
        this.nodeliverytimeto = nodeliverytimeto;
    }
    @Override
    public String toString() {
        return "KafkaRetryRequestBean [sMsgId=" + sMsgId + ", sDepId=" + sDepId + ", sAppId=" + sAppId + ", sMobileNo="
                + sMobileNo + ", sMsgText=" + sMsgText + ", sPin=" + sPin + ", sPopMailId=" + sPopMailId
                + ", sPopSenderAdd=" + sPopSenderAdd + ", stsgDetails=" + stsgDetails + ", sChannel=" + sChannel
                + ", sVendorCd=" + sVendorCd + ", sSenderCount=" + sSenderCount + ", topicName=" + topicName
                + ", fromdatetime=" + fromdatetime + ", todatetime=" + todatetime + ", nodeliverytimefrom="
                + nodeliverytimefrom + ", nodeliverytimeto=" + nodeliverytimeto + "]";
    }
    public KafkaRetryRequestBean(String sMsgId, String sDepId, String sAppId, String sMobileNo, String sMsgText,
            String sPin, String sPopMailId, String sPopSenderAdd, String stsgDetails, String sChannel, String sVendorCd,
            String sSenderCount, String topicName, String fromdatetime, String todatetime, String nodeliverytimefrom,
            String nodeliverytimeto) {
        super();
        this.sMsgId = sMsgId;
        this.sDepId = sDepId;
        this.sAppId = sAppId;
        this.sMobileNo = sMobileNo;
        this.sMsgText = sMsgText;
        this.sPin = sPin;
        this.sPopMailId = sPopMailId;
        this.sPopSenderAdd = sPopSenderAdd;
        this.stsgDetails = stsgDetails;
        this.sChannel = sChannel;
        this.sVendorCd = sVendorCd;
        this.sSenderCount = sSenderCount;
        this.topicName = topicName;
        this.fromdatetime = fromdatetime;
        this.todatetime = todatetime;
        this.nodeliverytimefrom = nodeliverytimefrom;
        this.nodeliverytimeto = nodeliverytimeto;
    }
    public KafkaRetryRequestBean() {
        super();
    }
    
 
}
 
 