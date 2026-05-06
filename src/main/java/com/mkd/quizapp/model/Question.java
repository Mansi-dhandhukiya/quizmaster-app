package com.mkd.quizapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
 import jakarta.persistence.Column;
 
@Data
@Entity
public class Question {
        
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

           @NotBlank(message = "Question title is required")
    private String questionTitle;

    @NotBlank(message = "Option 1 is required")
    private String option1;

    @NotBlank(message = "Option 2 is required")
    private String option2;

       @NotBlank(message = "Option 3 is required")
    private String option3;

    @NotBlank(message = "Option 4 is required")
    private String option4;

    @NotBlank(message = "Right answer is required")
    private String rightAnswer;

    @Column(name = "difficulty_level")
    @NotBlank(message = "Difficulty level is required")
    private String difficultyLevel;
     

   

      @NotBlank(message = "Category is required")
    private String category;
}
