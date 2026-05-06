package com.mkd.quizapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mkd.quizapp.model.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findAll();

    List<Question> findByCategory(String category);
   @Query(value = "SELECT * FROM question q WHERE LOWER(q.category) = LOWER(:category) ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
List<Question> findRandomQuestionsByCategory(
    @Param("category") String category,
    @Param("numQ") int numQ
);

    
} 


