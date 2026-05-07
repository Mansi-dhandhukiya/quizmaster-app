package com.mkd.quizapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkd.quizapp.model.Question;
import com.mkd.quizapp.service.QuestionService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("question")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return  questionService.findAll();
    }
    @GetMapping("category/{category}")
    public List<Question> getQuestionByCategory(@PathVariable String category){
        return questionService.getQuestionByCategory(category);
    }
   
    @PostMapping("add")
    public String addQuestion(@Valid @RequestBody Question question){
        return questionService.addQuestion(question);
    }
    @DeleteMapping("delete/{id}")
    public String deleteQuestion(@PathVariable Integer id){
        return questionService.deleteQuestion(id);
    }
    @PutMapping("update/{id}")
    public String updateQuestion(@PathVariable Integer id,@Valid @RequestBody Question question){
        return questionService.updateQuestion(id, question);
    }
}
