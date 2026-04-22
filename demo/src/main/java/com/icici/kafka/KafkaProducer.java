package com.icici.kafka;
 
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
 
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icici.dto.ChannelDataBean;
import com.icici.dto.KafkaErrorBean;
import com.icici.dto.KafkaSuccessBean;
 
@Component
public class KafkaProducer {
    
    private Logger log = LogManager.getLogger();
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    
    public void produceChannelsData(ChannelDataBean channelResponseDataBean)
    {
        log.info("Inside the String consumeChannelsData");
        String jsonString="";
         try
         {
             ObjectMapper mapper = new ObjectMapper();
                log.info("Inside the consumeChannelsData 2222");
                jsonString = mapper.writeValueAsString(channelResponseDataBean);
                log.info("jsonString=="+jsonString.toString());
        }
         catch (JsonProcessingException e) {
            log.error("Error in  produceChannelsData=",e);
            e.printStackTrace();
        }
         
         kafkaTemplate.send(channelResponseDataBean.getTopic_name().toUpperCase(),jsonString);
        
    }
    
    public void produceKafkaSuccessData(KafkaSuccessBean kafkaSuccessBean)
    {
        log.info("Inside the String produceKafkaSuccessData");
        String jsonString="";
         try
         {
             ObjectMapper mapper = new ObjectMapper();
                log.info("Inside the produceKafkaSuccessData 2222");
                jsonString = mapper.writeValueAsString(kafkaSuccessBean);
                log.info("jsonString=="+jsonString.toString());
        }
         catch (JsonProcessingException e) {
            log.error("Error in  produceKafkaSuccessData=",e);
            e.printStackTrace();
        }
         
         kafkaTemplate.send(kafkaSuccessBean.getTopic_name().toUpperCase(),jsonString);
        
    }
    
    public void produceKafkaErrorData(KafkaErrorBean kafkaErrorBean)
    {
        log.info("Inside the String produceKafkaErrorData");
        String jsonString="";
         try
         {
             ObjectMapper mapper = new ObjectMapper();
                log.info("Inside the produceKafkaErrorData 2222");
                jsonString = mapper.writeValueAsString(kafkaErrorBean);
                log.info("jsonString=="+jsonString.toString());
        }
         catch (JsonProcessingException e) {
            log.error("Error in  produceKafkaErrorData=",e);
            e.printStackTrace();
        }
         
         kafkaTemplate.send(kafkaErrorBean.getTopic_name().toUpperCase(),jsonString);
        
    }
 
}
 