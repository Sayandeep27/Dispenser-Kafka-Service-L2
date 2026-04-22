package com.icici.kafka;
 
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
 
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icici.dto.ChannelDataBean;
 
import com.icici.service.DispenserService;
 
@Component
public class KafkaConsumer
{
    private Logger log = LogManager.getLogger();
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @KafkaListener(topics = "SMSGW.RETRYMESSAGES", groupId = "dispenser-id")
    public void consumeChannelsData(String jsonString)
    {
        ChannelDataBean channelDataBean;
        try
        {
            log.info("Inside the String consumeChannelsData");
            log.info("jsonString==="+jsonString);
             try
             {
                 channelDataBean = objectMapper.readValue(jsonString,ChannelDataBean.class);
             log.info("kafkaRequestBean"+channelDataBean.toString());
            }
             catch (JsonProcessingException e) {
                log.error("Error in  kafkaOrchCOnsumer=",e);
                e.printStackTrace();
            }
            
        }catch(Exception ex)
        {
            log.error("Error occurred during cosuminfg data",ex);
        }
    }   
    
 
}
 
 