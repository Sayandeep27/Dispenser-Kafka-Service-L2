package com.icici.entity;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "DED_DSPNSR_ERR_DTLS")
public class SmsGatewayErrorBean {
    
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
    @Column(name = "MOBILE_NO")
    private String mobileNo;
    @Column(name = "ERR_DESC")
    private String errDesc;
    @Column(name = "ERR_SOURCE")
    private String errSource;
    @Column(name = "ERR_DTTIME")
    private String errDtTime;
    @Column(name = "ERR_STACK_TRACE")
    private String errStackTrace;
    @Column(name = "ERR_VENDOR_CODE")
    private String errVendorCode;
    
    @Column(name = "REQUEST_PARAM")
    private String requestParam;
 
    public SmsGatewayErrorBean() {
        super();
    }
 
    public SmsGatewayErrorBean(int id, int msgId, String deptId, String appId, String mobileNo, String errDesc,
            String errSource, String errDtTime, String errStackTrace, String errVendorCode, String requestParam) {
        super();
        this.id = id;
        this.msgId = msgId;
        this.deptId = deptId;
        this.appId = appId;
        this.mobileNo = mobileNo;
        this.errDesc = errDesc;
        this.errSource = errSource;
        this.errDtTime = errDtTime;
        this.errStackTrace = errStackTrace;
        this.errVendorCode = errVendorCode;
        this.requestParam = requestParam;
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
 
    public String getMobileNo() {
        return mobileNo;
    }
 
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
 
    public String getErrDesc() {
        return errDesc;
    }
 
    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }
 
    public String getErrSource() {
        return errSource;
    }
 
    public void setErrSource(String errSource) {
        this.errSource = errSource;
    }
 
    public String getErrDtTime() {
        return errDtTime;
    }
 
    public void setErrDtTime(String errDtTime) {
        this.errDtTime = errDtTime;
    }
 
    public String getErrStackTrace() {
        return errStackTrace;
    }
 
    public void setErrStackTrace(String errStackTrace) {
        this.errStackTrace = errStackTrace;
    }
 
    public String getErrVendorCode() {
        return errVendorCode;
    }
 
    public void setErrVendorCode(String errVendorCode) {
        this.errVendorCode = errVendorCode;
    }
 
    public String getRequestParam() {
        return requestParam;
    }
 
    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }
 
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
 
    @Override
    public String toString() {
        return "SmsSuccessDetails [id=" + id + ", msgId=" + msgId + ", deptId=" + deptId + ", appId=" + appId
                + ", mobileNo=" + mobileNo + ", errDesc=" + errDesc + ", errSource=" + errSource + ", errDtTime="
                + errDtTime + ", errStackTrace=" + errStackTrace + ", errVendorCode=" + errVendorCode
                + ", requestParam=" + requestParam + "]";
    }
 
}
 
 
 