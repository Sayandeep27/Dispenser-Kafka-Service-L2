package com.icici.dto;
 
public class KafkaSuccessBean {
    private String msg_id;
    private String dept_id;
    private String app_id;
    private String mobile_no;
    private String msg_text;
    private String pin;
    private String pop_mail_id;
    private String pop_sender_addr;
    private String tsc_details;
    private String channel;
    private String vendor_cd;
    private String send_count;
    private String topic_name;
    private String ack_id;
    private String vendor_response;
    
    
    public KafkaSuccessBean() {
        super();
    }
    public KafkaSuccessBean(String msg_id, String dept_id, String app_id, String mobile_no, String msg_text, String pin,
            String pop_mail_id, String pop_sender_addr, String tsc_details, String channel, String vendor_cd,
            String send_count, String topic_name, String ack_id, String vendor_response) {
        super();
        this.msg_id = msg_id;
        this.dept_id = dept_id;
        this.app_id = app_id;
        this.mobile_no = mobile_no;
        this.msg_text = msg_text;
        this.pin = pin;
        this.pop_mail_id = pop_mail_id;
        this.pop_sender_addr = pop_sender_addr;
        this.tsc_details = tsc_details;
        this.channel = channel;
        this.vendor_cd = vendor_cd;
        this.send_count = send_count;
        this.topic_name = topic_name;
        this.ack_id = ack_id;
        this.vendor_response = vendor_response;
    }
    public String getMsg_id() {
        return msg_id;
    }
    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }
    public String getDept_id() {
        return dept_id;
    }
    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }
    public String getApp_id() {
        return app_id;
    }
    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }
    public String getMobile_no() {
        return mobile_no;
    }
    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
    public String getMsg_text() {
        return msg_text;
    }
    public void setMsg_text(String msg_text) {
        this.msg_text = msg_text;
    }
    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }
    public String getPop_mail_id() {
        return pop_mail_id;
    }
    public void setPop_mail_id(String pop_mail_id) {
        this.pop_mail_id = pop_mail_id;
    }
    public String getPop_sender_addr() {
        return pop_sender_addr;
    }
    public void setPop_sender_addr(String pop_sender_addr) {
        this.pop_sender_addr = pop_sender_addr;
    }
    public String getTsc_details() {
        return tsc_details;
    }
    public void setTsc_details(String tsc_details) {
        this.tsc_details = tsc_details;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getVendor_cd() {
        return vendor_cd;
    }
    public void setVendor_cd(String vendor_cd) {
        this.vendor_cd = vendor_cd;
    }
    public String getSend_count() {
        return send_count;
    }
    public void setSend_count(String send_count) {
        this.send_count = send_count;
    }
    public String getTopic_name() {
        return topic_name;
    }
    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }
    public String getAck_id() {
        return ack_id;
    }
    public void setAck_id(String ack_id) {
        this.ack_id = ack_id;
    }
    public String getVendor_response() {
        return vendor_response;
    }
    public void setVendor_response(String vendor_response) {
        this.vendor_response = vendor_response;
    }
    @Override
    public String toString() {
        return "KafkaSuccessBean [msg_id=" + msg_id + ", dept_id=" + dept_id + ", app_id=" + app_id + ", mobile_no="
                + mobile_no + ", msg_text=" + msg_text + ", pin=" + pin + ", pop_mail_id=" + pop_mail_id
                + ", pop_sender_addr=" + pop_sender_addr + ", tsc_details=" + tsc_details + ", channel=" + channel
                + ", vendor_cd=" + vendor_cd + ", send_count=" + send_count + ", topic_name=" + topic_name + ", ack_id="
                + ack_id + ", vendor_response=" + vendor_response + "]";
    }
    
    
 
}
 
 