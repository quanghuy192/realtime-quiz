package org.example.realtimequiz.controllers;

import org.example.realtimequiz.controllers.request.UserRequest;
import org.example.realtimequiz.models.dto.UserDTO;
import org.example.realtimequiz.services.QuizGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RestApiController {

    @Autowired
    private QuizGameService quizGameService;

    @RequestMapping(value = "/api/v1/user/score", method = RequestMethod.POST)
    public ResponseEntity<?> updateScore(@RequestBody UserDTO userDTO) {
        quizGameService.publishScore(userDTO);
        return ResponseEntity.ok().build();
    }
}
