package home.bot.service.impl;

import static home.bot.dto.enums.PointType.DAILY_TYPE;
import static home.bot.dto.enums.ProgressStatus.IN_PROGRESS;

import home.bot.dto.UserPointDto;
import home.bot.dto.enums.PointType;
import home.bot.dto.enums.ProgressStatus;
import home.bot.entity.Comment;
import home.bot.entity.Point;
import home.bot.entity.Question;
import home.bot.entity.User;
import home.bot.entity.UserGroup;
import home.bot.repository.PointsRepository;
import home.bot.repository.UserGroupRepository;
import home.bot.service.PointsService;
import home.bot.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
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
public class PointsServiceImpl implements PointsService {

  private static final String DAILY_TITLE = "## **Вопросы к Daily** - %s \n";

  private final UserService userService;
  private final PointsRepository pointsRepository;
  private final UserGroupRepository userGroupRepository;

  //todo getPreviousActiveDailyPoint (не забыть про пятницу + выходные - предыдущий минус несколько дней) для дейликов
  //todo close old points and close questions and comments

  @Override
  @Transactional
  public String getTodayDailyPointsText(Long groupId) {
    UserGroup group = userGroupRepository.getById(groupId);
    StringBuilder builder = new StringBuilder(String.format(DAILY_TITLE, LocalDate.now()));
    group.getUsers()
        .stream()
        .map(User::getPoints)
        .flatMap(Collection::stream)
        .filter(point -> Objects.equals(point.getPointType(), DAILY_TYPE))
        .filter(point -> Objects.equals(point.getStatus(), IN_PROGRESS))
        .forEach(point -> createMessageForOpenUserQuestions(point, builder));
    return builder.toString();
  }

  @Override
  @Transactional
  public Point createTodayUserPoint(UserPointDto userPointDto) {
    User user = userService.getUserByAddress(userPointDto.getUserId());
    Point entity = pointsRepository.findByUserAndPointTypeAndStatusAndCreatedBefore(user, userPointDto.getType(),
            ProgressStatus.IN_PROGRESS, getEndDay())
        .map(point -> updatePoint(point, userPointDto))
        .orElseGet(() -> {
          Point point = new Point();
          point.setPointType(userPointDto.getType());
          point.setUser(user);
          return updatePoint(point, userPointDto);
        });

    return pointsRepository.save(entity);
  }

  @Override
  public String createMessageForOpenUserQuestions(String userAddress, PointType pointType, String title) {
    User user = userService.getUserByAddress(userAddress);
    StringBuilder builder = new StringBuilder(title);
    pointsRepository.findAllByUserAndPointType(user, pointType)
        .stream()
        .sorted(Comparator.comparing(Point::getCreated, Comparator.naturalOrder()))
        .forEach(point -> createMessageForOpenUserQuestions(point, builder));
    return builder.toString();
  }

  private String createMessageForOpenUserQuestions(Point point, StringBuilder builder) {
    User user = point.getUser();
    builder.append("***")
        .append(user.getFirstName())
        .append(" ")
        .append(user.getLastName())
        .append("***: \n");
    point.getQuestions()
        .stream()
        .filter(question -> Objects.equals(question.getStatus(), IN_PROGRESS))
        .sorted(Comparator.comparing(Question::getCreated, Comparator.naturalOrder()))
        .forEach(question -> {
          builder
              .append("1. ")
              .append(question.getText())
              .append("\n");
          question.getComments()
              .stream()
              .sorted(Comparator.comparing(Comment::getCreated, Comparator.naturalOrder()))
              .forEach(comment -> builder.append("    * ")
                  .append(comment.getUser().getFirstName())
                  .append(" ")
                  .append(comment.getUser().getLastName())
                  .append(" -> ")
                  .append(comment.getText())
                  .append("\n"));

        });
    return builder.toString();
  }

  private Point updatePoint(Point point, UserPointDto userPointDto) {
    if (Objects.nonNull(userPointDto.getOpenQuestions()) || !userPointDto.getOpenQuestions().isEmpty()) {
      point.getQuestions().addAll(createQuestions(userPointDto.getOpenQuestions()));
    }
    return point;
  }

  private Set<Question> createQuestions(Set<String> openQuestions) {
    return openQuestions.stream()
        .map(Question::new)
        .collect(Collectors.toSet());
  }

  private LocalDateTime getEndDay() {
    return LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
  }
}
