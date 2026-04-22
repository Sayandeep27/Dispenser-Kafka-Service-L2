package com.icici.dto;
 
public class KafkaErrorBean {
    private String msg_id;
    private String dept_id;
    private String app_id;
    private String mobile_no;
    private String err_desc;
    private String err_source;
    private String err_dttime;
    private String err_stack_trace;
    private String err_vendor_code;
    private String request_param;
    private String tsg_details;
    private String channel;
    private String vendor_cd ;
    private String topic_name;
    
    public KafkaErrorBean() {
        super();
    }
    public KafkaErrorBean(String msg_id, String dept_id, String app_id, String mobile_no, String err_desc,
            String err_source, String err_dttime, String err_stack_trace, String err_vendor_code, String request_param,
            String tsg_details, String channel, String vendor_cd, String topic_name) {
        super();
        this.msg_id = msg_id;
        this.dept_id = dept_id;
        this.app_id = app_id;
        this.mobile_no = mobile_no;
        this.err_desc = err_desc;
        this.err_source = err_source;
        this.err_dttime = err_dttime;
        this.err_stack_trace = err_stack_trace;
        this.err_vendor_code = err_vendor_code;
        this.request_param = request_param;
        this.tsg_details = tsg_details;
        this.channel = channel;
        this.vendor_cd = vendor_cd;
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
    public String getErr_desc() {
        return err_desc;
    }
    public void setErr_desc(String err_desc) {
        this.err_desc = err_desc;
    }
    public String getErr_source() {
        return err_source;
    }
    public void setErr_source(String err_source) {
        this.err_source = err_source;
    }
    public String getErr_dttime() {
        return err_dttime;
    }
    public void setErr_dttime(String err_dttime) {
        this.err_dttime = err_dttime;
    }
    public String getErr_stack_trace() {
        return err_stack_trace;
    }
    public void setErr_stack_trace(String err_stack_trace) {
        this.err_stack_trace = err_stack_trace;
    }
    public String getErr_vendor_code() {
        return err_vendor_code;
    }
    public void setErr_vendor_code(String err_vendor_code) {
        this.err_vendor_code = err_vendor_code;
    }
    public String getRequest_param() {
        return request_param;
    }
    public void setRequest_param(String request_param) {
        this.request_param = request_param;
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
    public String getTopic_name() {
        return topic_name;
    }
    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }
    @Override
    public String toString() {
        return "KafkaErrorBean [msg_id=" + msg_id + ", dept_id=" + dept_id + ", app_id=" + app_id + ", mobile_no="
                + mobile_no + ", err_desc=" + err_desc + ", err_source=" + err_source + ", err_dttime=" + err_dttime
                + ", err_stack_trace=" + err_stack_trace + ", err_vendor_code=" + err_vendor_code + ", request_param="
                + request_param + ", tsg_details=" + tsg_details + ", channel=" + channel + ", vendor_cd=" + vendor_cd
                + ", topic_name=" + topic_name + "]";
    }
    
    
    
 
}
 
 