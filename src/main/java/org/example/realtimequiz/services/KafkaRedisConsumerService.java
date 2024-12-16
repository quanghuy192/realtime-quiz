package org.example.realtimequiz.services;

import org.example.realtimequiz.models.dto.UserDTO;
import org.example.realtimequiz.models.entity.QuizUser;
import org.example.realtimequiz.utits.StringUtilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaRedisConsumerService {

    private final String TOPIC_SCORES = "scores";
    private final String KEY = "leader_board";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @KafkaListener(
            topics = TOPIC_SCORES,
            groupId = "${spring.kafka.groupid}")
    public void listenScores(String message) {
        var userDTO = (UserDTO) StringUtilities.toObject(message, UserDTO.class);
        redisTemplate.opsForZSet().incrementScore(KEY, userDTO.getId().toString(), userDTO.getScore());
    }

    private QuizUser getQuizUser(UserDTO userDTO) {
        var user = new QuizUser();
        modelMapper.map(userDTO, user);
        return user;
    }
}
