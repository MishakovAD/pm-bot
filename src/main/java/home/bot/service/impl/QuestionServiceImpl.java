package home.bot.service.impl;

import static home.bot.dto.enums.ProgressStatus.DONE;
import static home.bot.dto.enums.ProgressStatus.IN_PROGRESS;

import home.bot.entity.Point;
import home.bot.entity.Question;
import home.bot.entity.User;
import home.bot.repository.PointsRepository;
import home.bot.repository.QuestionRepository;
import home.bot.service.QuestionService;
import home.bot.service.UserService;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class QuestionServiceImpl implements QuestionService {

  private final UserService userService;
  private final PointsRepository pointsRepository;
  private final QuestionRepository questionRepository;

  @Override
  @Transactional
  public Set<Question> findOpenUserQuestions(String userAddress) {
    User user = userService.getUserByAddress(userAddress);
    return pointsRepository.findAllByUser(user)
        .stream()
        .sorted(Comparator.comparing(Point::getCreated, Comparator.naturalOrder()))
        .map(Point::getQuestions)
        .flatMap(Collection::stream)
        .filter(question -> Objects.equals(question.getStatus(), IN_PROGRESS))
        .collect(Collectors.toSet());
  }

  @Override
  @Transactional
  public void closeQuestion(Long questionId) {
    Question question = questionRepository.getById(questionId);
    question.setStatus(DONE);
    //todo: проверять, все ли вопросы поинта закрыты и закрывать его, если да
  }
}
