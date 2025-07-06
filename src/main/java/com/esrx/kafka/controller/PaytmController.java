package com.esrx.kafka.controller;
import com.esrx.kafka.dto.Payment;
import com.esrx.kafka.service.KafkaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paytm")
public class PaytmController {
    @Autowired
    KafkaService kafkaService;
    @GetMapping("/request1")
    public String sendPlainMessage(){
        kafkaService.sendPlainMessage();
        return "Message sent to queue";
    }
    @PostMapping("/request2")
    public String sendPaymentAsString(@RequestBody Payment payment) throws JsonProcessingException{
        return kafkaService.sendObjectAsString(payment);
    }

    @PostMapping("/request3")
    public Payment payment2(@RequestBody Payment payment) {
       return kafkaService.sendPaymentObject(payment);
    }

    @GetMapping("/request4")
    public String sendPlainMessages(){
        kafkaService.sendPlainMessages();
        return "Messages sent to queue";
    }

}
