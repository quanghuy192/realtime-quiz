package org.example.realtimequiz.models.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "score")
    private long score;

    @Column(name = "name")
    private String name;

    @Column(name = "createdAt")
    private long createdAt;

    @Column(name = "updatedAt")
    private long updatedAt;

    @Column(name = "deletedAt")
    private long deletedAt;
}
