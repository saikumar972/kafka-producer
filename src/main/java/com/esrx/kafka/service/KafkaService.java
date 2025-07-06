package com.esrx.kafka.service;

import com.esrx.kafka.dto.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaService {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${paytm.topic.name1}")
    String topic1;
    @Value("${paytm.topic.name2}")
    String topic2;
    @Value("${paytm.topic.name3}")
    String topic3;
    private static final ObjectMapper objectMapper=new ObjectMapper();

    public void sendPlainMessage(){
        String message="Plain text";
        CompletableFuture<SendResult<String, Object>> sendMessage = kafkaTemplate.send(topic1, message);
        sendMessage.whenComplete((result,exception)->{
            if(exception==null){
                System.out.println(message+" "+result.getRecordMetadata().offset());
            }else{
                System.out.println("Unable to send the message");
            }
        });

    }

    public String sendObjectAsString(Payment payment) throws JsonProcessingException {
        System.out.println("sending Payment msg to queue");
        objectMapper.registerModule(new JavaTimeModule());
        kafkaTemplate.send(topic2,objectMapper.writeValueAsString(payment));
        return payment.toString();
    }

    public Payment sendPaymentObject(Payment payment){
        System.out.println("sending Payment object to queue");
        kafkaTemplate.send(topic3,payment);
        return payment;
    }

}
