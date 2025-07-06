package com.esrx.kafka.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic createTopic1(){
        return new NewTopic("payment1",3,(short)1);
    }
    @Bean
    public NewTopic createTopic2(){
        return new NewTopic("payment2",3,(short)1);
    }
    @Bean
    public NewTopic createTopic3(){
        return new NewTopic("payment3",3,(short)1);
    }
    @Bean
    public Map<String,Object> producerConfig(){
        Map<String,Object> map=new HashMap<>();
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.springframework.kafka.support.serializer.JsonSerializer");
        return map;
    }
    @Bean
    public ProducerFactory<String,Object> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }
    @Bean
    public KafkaTemplate<String,Object> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
