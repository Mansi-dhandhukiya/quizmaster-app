package com.mkd.quizapp.service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mkd.quizapp.dto.QuestionWrapper;
import com.mkd.quizapp.dto.Response;
import com.mkd.quizapp.model.Question;
import com.mkd.quizapp.model.Quiz;
import com.mkd.quizapp.repository.QuestionDao;
import com.mkd.quizapp.repository.Quizdao;

@Service
public class QuizService {
    @Autowired
    Quizdao quizDao;
    @Autowired
    QuestionDao questionDao;

   public ResponseEntity<Integer> createQuiz(String category, int numQ, String title) {

    List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);

    if (questions.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Quiz quiz = new Quiz();
    quiz.setTitle(title);
    quiz.setQuestions(questions);

    quizDao.save(quiz);

    return new ResponseEntity<>(quiz.getId(), HttpStatus.OK); // ✅ return ID
}
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        try {
            Optional<Quiz> quiz = quizDao.findById(id);

            if (quiz.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<Question> questionsFromDB = quiz.get().getQuestions();
            List<QuestionWrapper> questionForUser = new ArrayList<>();

            for (Question q : questionsFromDB) {
                QuestionWrapper qw = new QuestionWrapper();
                qw.setId(q.getId()); // ← important for submit matching
                qw.setQuestionTitle(q.getQuestionTitle());
                qw.setOption1(q.getOption1());
                qw.setOption2(q.getOption2());
                qw.setOption3(q.getOption3());
                qw.setOption4(q.getOption4());
                questionForUser.add(qw);
            }

            return new ResponseEntity<>(questionForUser, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> calculateResult(Integer id, List<Response> responses) {
    try {
        Optional<Quiz> quiz = quizDao.findById(id);

        if (quiz.isEmpty()) {
            return new ResponseEntity<>("Quiz not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        List<Question> questions = quiz.get().getQuestions();
        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            String correct = questions.get(i).getRightAnswer();
            String given = responses.get(i).getResponse();

            // ← ADD THESE DEBUG LINES
            System.out.println("Q" + (i+1) + " Correct answer: [" + correct + "]");
            System.out.println("Q" + (i+1) + " User answer:    [" + given + "]");
            System.out.println("Q" + (i+1) + " Match: " + correct.equalsIgnoreCase(given));

            if (given != null && given.equalsIgnoreCase(correct)) {
                score++;
            }
        }

        int total = questions.size();
        String result = "You scored " + score + " out of " + total +
                        " (" + (score * 100 / total) + "%)";

        return new ResponseEntity<>(result, HttpStatus.OK);

    } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("Error calculating result", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

}
