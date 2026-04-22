package com.icici.entity;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "SKS_SMS_KAFKA_SUCCESS")
public class SmsSuccessDetails {
    
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "MSG_ID")
    private int msgId;
    @Column(name = "DEPT_ID")
    private String deptId;
    @Column(name = "APP_ID")
    private String appId;
    @Column(name = "PIN")
    private String pin;
    @Column(name = "POP_MAIL_ID")
    private String popMailId;
    @Column(name = "POP_SENDER_ADDR")
    private String popSenderAdd;
    @Column(name = "TSG_DETAILS")
    private String tsgDetails;
    @Column(name = "CHANNEL")
    private String channel;
    @Column(name = "MOBILE_NO")
    private String mobileNo;
    @Column(name = "SEND_COUNT")
    private String sendCount;
    @Column(name = "VENDOR_CD")
    private String vendorCd;
    @Column(name = "VENDOR_RESPONSE")
    private String vendorResponse;
    @Column(name = "STATUS_DATE")
    private String statusDate;
    @Column(name = "PARTITION_DTTIME")
    private String parttionTime;
    
    
    public SmsSuccessDetails() {
        super();
    }
    public SmsSuccessDetails(int id, int msgId, String deptId, String appId, String pin, String popMailId,
            String popSenderAdd, String tsgDetails, String channel, String mobileNo, String sendCount, String vendorCd,
            String vendorResponse, String statusDate, String parttionTime) {
        super();
        this.id = id;
        this.msgId = msgId;
        this.deptId = deptId;
        this.appId = appId;
        this.pin = pin;
        this.popMailId = popMailId;
        this.popSenderAdd = popSenderAdd;
        this.tsgDetails = tsgDetails;
        this.channel = channel;
        this.mobileNo = mobileNo;
        this.sendCount = sendCount;
        this.vendorCd = vendorCd;
        this.vendorResponse = vendorResponse;
        this.statusDate = statusDate;
        this.parttionTime = parttionTime;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getMsgId() {
        return msgId;
    }
    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }
    public String getDeptId() {
        return deptId;
    }
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }
    public String getPopMailId() {
        return popMailId;
    }
    public void setPopMailId(String popMailId) {
        this.popMailId = popMailId;
    }
    public String getPopSenderAdd() {
        return popSenderAdd;
    }
    public void setPopSenderAdd(String popSenderAdd) {
        this.popSenderAdd = popSenderAdd;
    }
    public String getTsgDetails() {
        return tsgDetails;
    }
    public void setTsgDetails(String tsgDetails) {
        this.tsgDetails = tsgDetails;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public String getSendCount() {
        return sendCount;
    }
    public void setSendCount(String sendCount) {
        this.sendCount = sendCount;
    }
    public String getVendorCd() {
        return vendorCd;
    }
    public void setVendorCd(String vendorCd) {
        this.vendorCd = vendorCd;
    }
    public String getVendorResponse() {
        return vendorResponse;
    }
    public void setVendorResponse(String vendorResponse) {
        this.vendorResponse = vendorResponse;
    }
    public String getStatusDate() {
        return statusDate;
    }
    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }
    public String getParttionTime() {
        return parttionTime;
    }
    public void setParttionTime(String parttionTime) {
        this.parttionTime = parttionTime;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    @Override
    public String toString() {
        return "SmsSuccessDetails [id=" + id + ", msgId=" + msgId + ", deptId=" + deptId + ", appId=" + appId + ", pin="
                + pin + ", popMailId=" + popMailId + ", popSenderAdd=" + popSenderAdd + ", tsgDetails=" + tsgDetails
                + ", channel=" + channel + ", mobileNo=" + mobileNo + ", sendCount=" + sendCount + ", vendorCd="
                + vendorCd + ", vendorResponse=" + vendorResponse + ", statusDate=" + statusDate + ", parttionTime="
                + parttionTime + "]";
    }
    
    
    
 
}
 
 