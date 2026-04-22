package com.icici.dto;
 
import com.fasterxml.jackson.annotation.JsonProperty;
 
public class ChannelsData {
    
    @JsonProperty("msg_id")
    private String msg_id;
    @JsonProperty("dept_id")
    private String dept_id;
    @JsonProperty("app_id")
    private String app_id;
    @JsonProperty("mobile_no")
    private String mobile_no;
//  @JsonProperty("msg_text")
//  private String DEPTMSGID;
    @JsonProperty("msg_text")
    private String msg_text;
    @JsonProperty("fromdatetime")
    private String fromdatetime;
    @JsonProperty("todatetime")
    private String todatetime;
    @JsonProperty("nodeliverytimefrom")
    private String nodeliverytimefrom;
    @JsonProperty("nodeliverytimeto")
    private String nodeliverytimeto;
    @JsonProperty("pin")
    private String pin;
    @JsonProperty("pop_mail_id")
    private String pop_mail_id;
    @JsonProperty("pop_sender_addr")
    private String pop_sender_addr;
    @JsonProperty("tsg_details")
    private String tsg_details;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("vendor_cd")
    private String vendor_cd;
    @JsonProperty("send_count")
    private String send_count;
    @JsonProperty("topic_name")
    private String topic_name;
    
    public ChannelsData() {
        super();
    }
    public ChannelsData(String msg_id, String dept_id, String app_id, String mobile_no, String msg_text,
            String fromdatetime, String todatetime, String nodeliverytimefrom, String nodeliverytimeto, String pin,
            String pop_mail_id, String pop_sender_addr, String tsg_details, String channel, String vendor_cd,
            String send_count, String topic_name) {
        super();
        this.msg_id = msg_id;
        this.dept_id = dept_id;
        this.app_id = app_id;
        this.mobile_no = mobile_no;
        this.msg_text = msg_text;
        this.fromdatetime = fromdatetime;
        this.todatetime = todatetime;
        this.nodeliverytimefrom = nodeliverytimefrom;
        this.nodeliverytimeto = nodeliverytimeto;
        this.pin = pin;
        this.pop_mail_id = pop_mail_id;
        this.pop_sender_addr = pop_sender_addr;
        this.tsg_details = tsg_details;
        this.channel = channel;
        this.vendor_cd = vendor_cd;
        this.send_count = send_count;
        this.topic_name = topic_name;
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
    public String getTsg_details() {
        return tsg_details;
    }
    public void setTsg_details(String tsg_details) {
        this.tsg_details = tsg_details;
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
    @Override
    public String toString() {
        return "ChannelsData [msg_id=" + msg_id + ", dept_id=" + dept_id + ", app_id=" + app_id + ", mobile_no="
                + mobile_no + ", msg_text=" + msg_text + ", fromdatetime=" + fromdatetime + ", todatetime=" + todatetime
                + ", nodeliverytimefrom=" + nodeliverytimefrom + ", nodeliverytimeto=" + nodeliverytimeto + ", pin="
                + pin + ", pop_mail_id=" + pop_mail_id + ", pop_sender_addr=" + pop_sender_addr + ", tsg_details="
                + tsg_details + ", channel=" + channel + ", vendor_cd=" + vendor_cd + ", send_count=" + send_count
                + ", topic_name=" + topic_name + "]";
    }
    
    
 
}
 
 