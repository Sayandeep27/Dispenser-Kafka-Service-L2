package com.icici.dto;
 
public class SmsGatewayRequestBody {
    //"mobile_no": "@mobile_no","sender_id": "@senderid","message": "@message_text","msgid": "@msg_id","deptid": "@dept_id","appid": "@app_id","SMS_TYPE": "Trans/OTP","vendor_cd": "@vendor_cd","channel": "sms"
 
    private String mobile_no;
    private String sender_id;
    private String message;
    private String msgid;
    private String deptid;
    private String appid;
    private String SMS_TYPE;
    private String vendor_cd;
    private String channel;
    
    
    public SmsGatewayRequestBody() {
        super();
    }
    public SmsGatewayRequestBody(String mobile_no, String sender_id, String message, String msgid, String deptid,
            String appid, String sMS_TYPE, String vendor_cd, String channel) {
        super();
        this.mobile_no = mobile_no;
        this.sender_id = sender_id;
        this.message = message;
        this.msgid = msgid;
        this.deptid = deptid;
        this.appid = appid;
        SMS_TYPE = sMS_TYPE;
        this.vendor_cd = vendor_cd;
        this.channel = channel;
    }
    public String getMobile_no() {
        return mobile_no;
    }
    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
    public String getSender_id() {
        return sender_id;
    }
    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMsgid() {
        return msgid;
    }
    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }
    public String getDeptid() {
        return deptid;
    }
    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }
    public String getAppid() {
        return appid;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }
    public String getSMS_TYPE() {
        return SMS_TYPE;
    }
    public void setSMS_TYPE(String sMS_TYPE) {
        SMS_TYPE = sMS_TYPE;
    }
    public String getVendor_cd() {
        return vendor_cd;
    }
    public void setVendor_cd(String vendor_cd) {
        this.vendor_cd = vendor_cd;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    @Override
    public String toString() {
        return "SmsGatewayRequestBody [mobile_no=" + mobile_no + ", sender_id=" + sender_id + ", message=" + message
                + ", msgid=" + msgid + ", deptid=" + deptid + ", appid=" + appid + ", SMS_TYPE=" + SMS_TYPE
                + ", vendor_cd=" + vendor_cd + ", channel=" + channel + "]";
    }
    
    
}
 
 