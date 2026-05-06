package com.mkd.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkd.quizapp.model.Quiz;

public interface Quizdao extends JpaRepository<Quiz, Integer> {

    
} 
