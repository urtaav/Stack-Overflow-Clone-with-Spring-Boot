package com.urtaav.repositories;

import com.urtaav.entities.Answers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answers,Long> {
    List<Answers> findAllByQuestionId(Long questionId);
}
