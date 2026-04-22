package com.icici.service;
 
import java.util.List;
 
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.icici.dto.ChannelDataBean;
import com.icici.dto.ChannelsData;
import com.icici.dto.DispenseOutputBean;
import com.icici.dto.KafkaErrorBean;
import com.icici.dto.KafkaRetryRequestBean;
import com.icici.dto.KafkaSuccessBean;
import com.icici.dto.SmsGatewayRequestBody;
import com.icici.entity.SmsGatewayErrorBean;
import com.icici.entity.SmsSuccessDetails;
import com.icici.kafka.KafkaProducer;
import com.icici.repository.DispenserRepo;
import com.icici.repository.SmsErrorRepository;
import com.icici.repository.SmsSuccessRepository;
import com.icici.security.EncryptionService;
 
@Service
public class DispenserService {
    private static Logger log = LogManager.getLogger();
    
    private DispenserRepo dispenserRepo;
    private SmsErrorRepository smsErrorRepository;
    private SmsSuccessRepository smsSuccessRepository;
    private EncryptionService encryptionService;
    private KafkaProducer kafkaProducer;
     @Autowired
     private ObjectMapper objectMapper;
     @Autowired
     private RestTemplate restTemplate;
    
    public DispenserService(DispenserRepo dispenserRepo,SmsErrorRepository smsErrorRepository,SmsSuccessRepository smsSuccessRepository
            ,KafkaProducer kafkaProducer,EncryptionService encryptionService)
    {
        this.dispenserRepo=dispenserRepo;
        this.smsErrorRepository=smsErrorRepository;
        this.smsSuccessRepository=smsSuccessRepository;
        this.kafkaProducer=kafkaProducer;
        this.encryptionService=encryptionService;
    }
    
   public void SmsProcessing(ChannelDataBean channelDataBean) throws Exception
    {
       log.info("Inside the SmsProcessing");
       String jsonString="",smsType="Transaction";
       SmsGatewayRequestBody smsGatewayRequestBody=null;
       List<DispenseOutputBean> listUrl= dispenserRepo.fechSmsGatewayDetails(channelDataBean);
       
       for(DispenseOutputBean list : listUrl)
       {
            try {
                log.info("Inside the kafka Procuder");
                smsGatewayRequestBody = objectMapper.readValue(list.getJsonURLData(), SmsGatewayRequestBody.class);
                log.info("jsonString==" + smsGatewayRequestBody.toString());
            } catch (JsonProcessingException e) {
                log.error("Error Occurred at Json Converter", e);
 
            }
            if (channelDataBean.getPin().toUpperCase() == "ICICIT" || channelDataBean.getPin().toUpperCase() == "ICICIS" || channelDataBean.getPin().toUpperCase() == "142424" || channelDataBean.getPin().toUpperCase() == "42424" || channelDataBean.getPin().toUpperCase() == "ICIEMP" || channelDataBean.getPin().toUpperCase() == "ICICIH" || channelDataBean.getPin().toUpperCase() == "ISECLD")
            {
                smsType="Transaction";
            }
            else if (channelDataBean.getPin().toUpperCase() == "ICICIO")
            {
                smsType="OTP";
            }
            smsGatewayRequestBody.setAppid(channelDataBean.getApp_id());
            smsGatewayRequestBody.setChannel(channelDataBean.getChannel());
            smsGatewayRequestBody.setDeptid(channelDataBean.getDept_id());
            smsGatewayRequestBody.setMessage(channelDataBean.getMsg_text());
            smsGatewayRequestBody.setMobile_no(channelDataBean.getMobile_no());
            smsGatewayRequestBody.setMsgid(channelDataBean.getMsg_id());
            smsGatewayRequestBody.setSender_id(channelDataBean.getPin().toUpperCase());
            smsGatewayRequestBody.setSMS_TYPE(smsType);
            smsGatewayRequestBody.setVendor_cd(channelDataBean.getVendor_cd());
            
//          String jsonString="";
             try
             {
                 ObjectMapper mapper = new ObjectMapper();
                    log.info("Inside the produceKafkaErrorData 2222");
                    jsonString = mapper.writeValueAsString(smsGatewayRequestBody);
                    log.info("jsonString=="+jsonString.toString());
            }
             catch (JsonProcessingException e) {
                log.error("Error in  produceKafkaErrorData=",e);
                e.printStackTrace();
            }
            
            String encryptedPayload=encryptionService.encryptPayload(jsonString,channelDataBean.getMsg_id());
            log.info("encryptedPayload="+encryptedPayload);
            
            try
            {
                String decryptedResponse=smsGatewayApiCall(list.getUrl(),encryptedPayload);
                log.info("Decrypted SMS Gateway Response="+decryptedResponse);
                log.info("After SMSGAteway Call");
                
    
                KafkaSuccessBean kafkaSuccessBean=new KafkaSuccessBean();
                kafkaSuccessBean.setMsg_id(channelDataBean.getMsg_id());
                kafkaSuccessBean.setDept_id(channelDataBean.getDept_id());
                kafkaSuccessBean.setApp_id(channelDataBean.getApp_id());
                kafkaSuccessBean.setMobile_no(channelDataBean.getMobile_no());
                kafkaSuccessBean.setMsg_text(channelDataBean.getMsg_text());
                kafkaSuccessBean.setPin(channelDataBean.getPin());
                kafkaSuccessBean.setPop_mail_id(channelDataBean.getPop_mail_id());
                kafkaSuccessBean.setPop_sender_addr(channelDataBean.getPop_sender_addr());
                kafkaSuccessBean.setTsc_details(channelDataBean.getTsg_details());
                kafkaSuccessBean.setChannel(channelDataBean.getChannel());
                kafkaSuccessBean.setVendor_cd(channelDataBean.getVendor_cd());
                kafkaSuccessBean.setSend_count(channelDataBean.getSend_count());
                kafkaSuccessBean.setTopic_name(channelDataBean.getTopic_name());
                kafkaSuccessBean.setAck_id("");
                kafkaSuccessBean.setVendor_response("");
                
                kafkaProducer.produceKafkaSuccessData(kafkaSuccessBean);
                log.info("After Kafka Success Data Produce");
                
                
            }
            catch(Exception ex)
            {
                log.error("Error Occurred at During SmsGateway call", ex);
                log.info("Before Kafka Error Data Producer");
                channelDataBean.setSend_count(String.valueOf(Integer.valueOf(channelDataBean.getSend_count())+1));
                kafkaErrorSmsProducer(channelDataBean);
                log.info("After Kafka Error Data Producer");
                kafkaChannelSmsProducer(channelDataBean);
                log.info("After Kafka kafkaChannelSmsProducer");
                log.info("After Kafka Channel Data Producer");
            }
            
       }
       
     
       log.info("End of the SmsProcessing");
    }
   
   public String smsGatewayApiCall(String url,String encryptedPayload) throws Exception
   {
       log.info("Inside the smsGatewayApiCall");
    // Set Headers
       org.springframework.http.HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);
    // Wrap request in HttpEntity
       HttpEntity<String> entity = new HttpEntity<>(encryptedPayload, headers);
       log.info("Inside the smsGatewayApiCall111");
       // Send POST request and read response as encrypted String
       ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
       log.info("Inside the smsGatewayApiCall2222");
       String responseBody = response.getBody();
       log.info("Encrypted Response: " + responseBody);
       return encryptionService.decryptPayload(responseBody);
   }
   
   public void KafkaSuccessInseret(ChannelDataBean channelDataBean)
   {
       log.info("Inside the smsGatewayApiCall");
       try
       {
           SmsSuccessDetails smsSuccessDetails=new SmsSuccessDetails();
            smsSuccessDetails.setAppId(channelDataBean.getApp_id());
            smsSuccessDetails.setChannel("JIO");
            smsSuccessDetails.setDeptId(channelDataBean.getDept_id());
//          smsSuccessDetails.setId(0);
            smsSuccessDetails.setMobileNo(channelDataBean.getMobile_no());
            smsSuccessDetails.setMsgId(Integer.valueOf(channelDataBean.getMsg_id()));
            smsSuccessDetails.setParttionTime("");
            smsSuccessDetails.setPin(channelDataBean.getPin());
            smsSuccessDetails.setPopMailId(channelDataBean.getPop_mail_id());
            smsSuccessDetails.setPopSenderAdd(channelDataBean.getPop_sender_addr());
            smsSuccessDetails.setSendCount(channelDataBean.getSend_count());
            smsSuccessDetails.setStatusDate("");
            smsSuccessDetails.setTsgDetails(channelDataBean.getTsg_details());
            smsSuccessDetails.setVendorCd(channelDataBean.getVendor_cd());
            smsSuccessDetails.setVendorResponse("");
            smsSuccessRepository.save(smsSuccessDetails);
            log.info("After Succefuuly data insertions");
            
       }catch(Exception ex)
       {
           log.error("Error Occurred at Kafka Success Insetion",ex);
       }
       
   }
   
   public void smsGatewayErrorInsert(ChannelDataBean channelDataBean)
   {
       log.info("Inside the smsGateErrorInsert");
       try
       {
           SmsGatewayErrorBean SmsGatewayErrorBean=new SmsGatewayErrorBean();
           SmsGatewayErrorBean.setAppId(null);
           SmsGatewayErrorBean.setDeptId(null);
           SmsGatewayErrorBean.setErrDesc(null);
           SmsGatewayErrorBean.setErrDtTime(null);
           SmsGatewayErrorBean.setErrSource(null);
           SmsGatewayErrorBean.setErrStackTrace(null);
           SmsGatewayErrorBean.setErrVendorCode(null);
           SmsGatewayErrorBean.setMobileNo(null);
           SmsGatewayErrorBean.setMsgId(0);
           SmsGatewayErrorBean.setRequestParam(null);
           
           smsErrorRepository.save(SmsGatewayErrorBean);
            log.info("After Succefuuly data insertions for smsgateway error");
           
       }
       catch(Exception ex)
       {
           log.error("Error Occurred at SMS Gateway Error Insetion",ex);
       }
       
   }
   
   public void kafkaErrorSmsProducer(ChannelDataBean channelDataBean)
   {
       log.info("Inside the kafkaErrorSmsProducer");
       try
       {
           KafkaErrorBean kafkaErrorBean=new KafkaErrorBean();
           kafkaErrorBean.setApp_id(channelDataBean.getApp_id());
           kafkaErrorBean.setChannel(channelDataBean.getChannel());
           kafkaErrorBean.setDept_id(channelDataBean.getDept_id());
           kafkaErrorBean.setErr_desc("");
           kafkaErrorBean.setErr_dttime("");
           kafkaErrorBean.setErr_source("");
           kafkaErrorBean.setErr_stack_trace("");
           kafkaErrorBean.setErr_vendor_code(channelDataBean.getVendor_cd());
           kafkaErrorBean.setMobile_no(channelDataBean.getMobile_no());
           kafkaErrorBean.setMsg_id(channelDataBean.getMsg_id());
           kafkaErrorBean.setRequest_param("");
           kafkaErrorBean.setTopic_name("SMSGW.DED.DISPERRDETAILS");
           
           kafkaProducer.produceKafkaErrorData(kafkaErrorBean);
           
           log.info("After the kafkaErrorSmsProducer");
           
           
       }
       catch(Exception ex)
       {
           log.error("Error Occurred at kafkaErrorSmsProducer",ex);
       }
       
   }
   
   public void kafkaChannelSmsProducer(ChannelDataBean channelDataBean)
   {
       log.info("Inside the kafkaChannelSmsProducer");
       try
       {
           
           channelDataBean.setSend_count(String.valueOf(Integer.valueOf(channelDataBean.getSend_count())+1));
           kafkaProducer.produceChannelsData(channelDataBean);
           
           log.info("After the kafkaChannelSmsProducer");
           
           
       }
       catch(Exception ex)
       {
           log.error("Error Occurred at kafkaChannelSmsProducer",ex);
       }
       
   }
   
 
}
 
 
