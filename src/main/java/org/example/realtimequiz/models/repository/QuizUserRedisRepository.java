package org.example.realtimequiz.models.repository;

import java.util.UUID;

import org.example.realtimequiz.models.entity.QuizUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizUserRedisRepository extends CrudRepository<QuizUser, UUID> {
}
