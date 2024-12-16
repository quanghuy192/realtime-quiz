package org.example.realtimequiz.services;

import org.example.realtimequiz.models.dto.UserDTO;
import org.example.realtimequiz.models.entity.QuizUser;
import org.example.realtimequiz.models.repository.QuizUserPostgresRepository;
import org.example.realtimequiz.utits.StringUtilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaPostgresConsumerService {

    private final String TOPIC_SCORES = "scores";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QuizUserPostgresRepository quizUserPostgresRepository;

    @KafkaListener(
            topics = TOPIC_SCORES,
            groupId = "${spring.kafka.groupid}")
    public void listenScores(String message) {
        var userDTO = (UserDTO) StringUtilities.toObject(message, UserDTO.class);

        var user = quizUserPostgresRepository.findById(userDTO.getId())
                .orElse(null);
        if (user == null) {
            quizUserPostgresRepository.save(getQuizUser(userDTO));
        } else {
            user.setScore(userDTO.getScore());
            user.setUpdatedAt(userDTO.getUpdatedAt());
            quizUserPostgresRepository.save(user);
        }
    }

    private QuizUser getQuizUser(UserDTO userDTO) {
        var user = new QuizUser();
        modelMapper.map(userDTO, user);
        return user;
    }
}
