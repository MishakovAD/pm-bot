package home.bot.service;

import home.bot.entity.Question;
import java.util.Set;

public interface QuestionService {

  Set<Question> findOpenUserQuestions(String userAddress);

  void closeQuestion(Long questionId);

}
