package com.mkd.quizapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mkd.quizapp.model.Question;
import com.mkd.quizapp.repository.QuestionDao;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
      
  public ResponseEntity<List<Question>> findAll(){
    try{
         return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
    }catch(Exception e){
        e.printStackTrace();
    }   
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

   
  }
  
  public List<Question> getQuestionByCategory(String category) {
    return questionDao.findByCategory(category);
  }

  public String addQuestion(Question question) {
    
     try {
      questionDao.save(question);
    return "Question added successfully";
    } catch (Exception e) {
        e.printStackTrace();  // 👈 THIS WILL SHOW REAL ERROR
        throw e;
    }
  }

  public String deleteQuestion(Integer id) {
    try {
      questionDao.deleteById(id);
      return "Question deleted successfully";
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  public String updateQuestion(Integer id, Question question){
    try{
        Question existingQuestion = questionDao.findById(id).orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
        existingQuestion.setQuestionTitle(question.getQuestionTitle());
        existingQuestion.setOption1(question.getOption1());
        existingQuestion.setOption2(question.getOption2());
        existingQuestion.setOption3(question.getOption3());
        existingQuestion.setOption4(question.getOption4());
        existingQuestion.setRightAnswer(question.getRightAnswer());
        existingQuestion.setDifficultyLevel(question.getDifficultyLevel());
        existingQuestion.setCategory(question.getCategory());
        questionDao.save(existingQuestion);
        return "Question updated successfully";
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
