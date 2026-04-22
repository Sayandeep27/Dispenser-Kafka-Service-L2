package com.icici.repository;
 
import java.util.List;
 
import com.icici.dto.ChannelDataBean;
import com.icici.dto.DispenseOutputBean;
 
public interface DispenserRepo {
    
    public List<DispenseOutputBean> fechSmsGatewayDetails(ChannelDataBean channelDataBean);
 
}
 