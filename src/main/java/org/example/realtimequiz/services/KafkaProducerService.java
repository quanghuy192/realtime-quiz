package org.example.realtimequiz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplateProducer;

    public void sendMessage(String topicName, String msg) {
        kafkaTemplateProducer.send(topicName, msg);
    }
}
