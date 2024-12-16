package org.example.realtimequiz.models.repository;

import java.util.UUID;

import org.example.realtimequiz.models.entity.QuizUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizUserPostgresRepository extends JpaRepository<QuizUser, UUID> {
}
