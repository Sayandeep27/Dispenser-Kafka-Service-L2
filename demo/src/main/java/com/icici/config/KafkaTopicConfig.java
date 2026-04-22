/*package com.icici.config;
 
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
 
@Configuration
public class KafkaTopicConfig {
    
    @Value("${topic-partitions}")
    int noOfPartitions;
    
    @Bean
    public NewTopic listenerTopic() {
        return TopicBuilder.name("VISPL")
                .partitions(noOfPartitions)
                .build();
    }
}
*/
 