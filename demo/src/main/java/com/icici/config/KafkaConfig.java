package com.icici.config;
 
import java.util.HashMap;
import java.util.Map;
 
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
 
 
@Configuration
public class KafkaConfig {
    
    private static Logger log = LogManager.getLogger();
 
//  @Value("${ssl.keystore.path}")
//  private String keyStorePath;
    
//  @Value("${ssl.keystore.path:defaultValue}")
//  private String keyStorePath;
//
//  @Value("${ssl.keystore.password:defaultValue}")
//  private String keyStorePassword;
//
//  @Value("${ssl.truststore.path:defaultValue}")
//  private String trustStorePath;
//
//  @Value("${ssl.truststore.password:defaultValue}")
//  private String trustStorePassword;
//
//  @Value("${kafka.bootstrap.server:defaultValue}")
//  private String bootStrapServer;
//  
//  @Value("${sslFlag:defaultValue}")
//  private String sslFlag;
 
//  @Bean
//  public ProducerFactory<String, String> producerFactoryICore() {
//      Map<String, Object> config = new HashMap<String, Object>();
//      config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
//      config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//      config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//      if (sslFlag.equalsIgnoreCase("Y")) {
//      config.put("security.protocol", "SSL");
//      config.put("ssl.truststore.location", trustStorePath); // Path to the truststore file
//      config.put("ssl.truststore.password", trustStorePassword); // Truststore password
//      config.put("ssl.keystore.location", keyStorePath); // Path to the keystore file
//      config.put("ssl.keystore.password", keyStorePassword); // Keystore password
//      config.put("ssl.client.auth", "required");
//      config.put("ssl.enabled.protocols", "TLSv1.2,TLSv1.1,TLSv1");
//      }
//      return new DefaultKafkaProducerFactory<String, String>(config);
//  }
 
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<String, String>(producerFactoryICore());
    }
    
    @Bean
    public ProducerFactory<String, String> producerFactoryICore() {
        Map<String, Object> config = new HashMap<String, Object>();
        try {
        
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.75.49.155:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        
        }catch(Exception ex)
        {
            log.error("Error occurred at producerFactoryICore=",ex);
            
        }
        return new DefaultKafkaProducerFactory<String, String>(config);
        
    }
    
    
//  @Bean
//    ConcurrentKafkaListenerContainerFactory<String, String>
//                        kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                                new ConcurrentKafkaListenerContainerFactory<String, String>();
//        factory.setConsumerFactory(consumerFactory);
//        return factory;
//    }
 
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<String, String>(consumerProps());
    }
 
//    private Map<String, Object> consumerProps() {
//      System.out.println("inside consumerProps :: ");
//      Map<String, Object> props = new HashMap<String, Object>();
//      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
//      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//      props.put(ConsumerConfig.GROUP_ID_CONFIG, "ereconapi");
//      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//      if (sslFlag.equalsIgnoreCase("Y")) {
//      props.put("security.protocol", "SSL");
//      props.put("ssl.truststore.location", trustStorePath); // Path to the truststore file
//      props.put("ssl.truststore.password", trustStorePassword); // Truststore password
//      props.put("ssl.keystore.location", keyStorePath); // Path to the keystore file
//      props.put("ssl.keystore.password", keyStorePassword); // Keystore password
//      props.put("ssl.client.auth", "required");
//      props.put("ssl.enabled.protocols", "TLSv1.2,TLSv1.1,TLSv1");
//      }
//        // ...
//        return props;
//    }
    
    private Map<String, Object> consumerProps() {
        System.out.println("inside consumerProps :: ");
        Map<String, Object> props = new HashMap<String, Object>();
        try {
//      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.75.49.155:9092");
//      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
////        props.put(ConsumerConfig.GROUP_ID_CONFIG, "ereconapi");
//      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.75.49.155:9092");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//          props.put(ConsumerConfig.GROUP_ID_CONFIG, "orchestrator");
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "dispenser-id");
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            
        
        }catch(Exception ex)
        {
            log.error("Error occurred at consumerProps=",ex);
            
        }
        
        // ...
        return props;
    }
 
 
}
 
 