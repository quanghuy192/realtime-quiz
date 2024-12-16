package org.example.realtimequiz.services;

import org.example.realtimequiz.models.dto.UserDTO;
import org.example.realtimequiz.utits.StringUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QuizGameService {

    private final String TOPIC_SCORES = "scores";

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public void publishScore(UserDTO userDTO) {
        var message = StringUtilities.toJson(userDTO);
        kafkaProducerService.sendMessage(TOPIC_SCORES, message);
    }
}
