package com.icici.dto;
 
public class DispenseOutputBean {
    
    private String url;
    private String jsonURLData;
    
    public DispenseOutputBean() {
        super();
    }
    public DispenseOutputBean(String url, String jsonURLData) {
        super();
        this.url = url;
        this.jsonURLData = jsonURLData;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getJsonURLData() {
        return jsonURLData;
    }
    public void setJsonURLData(String jsonURLData) {
        this.jsonURLData = jsonURLData;
    }
    @Override
    public String toString() {
        return "DispenseOutputBean [url=" + url + ", jsonURLData=" + jsonURLData + "]";
    }
 
}
 
 