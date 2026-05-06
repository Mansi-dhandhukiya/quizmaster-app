package com.mkd.quizapp.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Quiz {
    //Primary key
    // title
    // questions
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String title;

    @ManyToMany(fetch = FetchType.EAGER) 
    private List<Question> questions;

}
