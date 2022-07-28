package home.bot.dto.enums;

import static home.bot.dto.enums.MattermostDialogElementType.SELECT;
import static home.bot.dto.enums.MattermostDialogElementType.TEXT;
import static home.bot.dto.enums.MattermostDialogElementType.TEXTAREA;

import home.bot.dto.AddUserToGroupDto;
import home.bot.dto.CreateGroupDto;
import home.bot.dto.CreateTaskDto;
import home.bot.dto.UserPointDto;
import home.bot.dto.UserRegistrationDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@RequiredArgsConstructor
public enum MattermostDialogElementValue {
  REGISTRATION_FIRST_NAME("firstNameReg", "Имя",  null,"Иван", TEXT, false),
  REGISTRATION_LAST_NAME("lastNameReg", "Фамилия", "Иванов", null, TEXT, false),
  REGISTRATION_MIDDLE_NAME("middleNameReg", "Отчество", "Иванович", null, TEXT, true),
  REGISTRATION_BIRTHDATE("birthdateReg", "Дата Рождения", "ДД.MM.ГГГГ", null, TEXT, false),
  REGISTRATION_POSITION("positionReg", "Профессия",  null,"Кошка, которая гуляет сама по себе", SELECT, false),
  REGISTRATION_PHONE_NUMBER("phoneNumberReg", "Телефон",  null,"+7ХХХХХХХХХХ", TEXT, false),
  REGISTRATION_EMAIL("emailReg", "Рабочий e-mail",  null,"МестоДляВашейРекламы@email.ru", TEXT, false),

  //create group (admin)
  GROUP_NAME("groupName", "Название группы",  null,"Название Группы", TEXT, false),
  GROUP_CHANNEL_ID("groupChannelId", "ChannelId канала группы.",  null,"Channel -> View Info -> ID (серым маленьким шрифтом)", TEXT, false),
  GROUP_EMAIL("groupEmail", "Почтовая группа",  null,"email@email.org", TEXT, false),
  GROUP_DESCRIPTION("groupDescription", "Краткая информация",  null,"Самая лучшая группа на свете.", TEXTAREA, true),
  AVAILABLE_GROUPS_BY_USER("availableGroupsByUser", "Группа:",  null,"Название группы", SELECT, false),
  AVAILABLE_USERS_FOR_ADD_TO_GROUP("availableUsersForGroup", "Пользователь:",  null,"Иван Иванов", SELECT, false),


  //user flows
  USER_POINTS_TYPES("userPointsType", "Тип вопросов.",  null,"К Daily, например", SELECT, false),
  USER_POINTS_OPEN_QUESTIONS("userPointOpenQuestions", "Открытые вопросы, которые надо обсудить.",
      null,"Опиши каждый вопрос с новой строки.", TEXTAREA, false),
  USER_POINTS_JIRA_TASKS("userPointJiraTasks", "Номера задач, которые нужно подсветить.",
      null,"Введи каждый номер с новой строки.", TEXTAREA, true),
  USER_POINTS_OTHER_PROBLEMS("userPointOtherProblems", "Место для того, что расстраивает или бесит.",
      null,"Новая проблема -> новая строка, \nуже запомнить должны.", TEXTAREA, true),
  OPEN_USER_QUESTIONS("openUserQuestions", "Открытые вопросы.",
      null,"Выбери вопрос, который нужно закрыть.", SELECT, true),
  USER_COMMENTS_TO_QUESTION("userCommentsToQuestion", "Комментарии к выбранному вопросу.",
      null,"Опиши каждый комментарий с новой строки.", TEXTAREA, false),

  //tasks
  USER_TASK_NAME("userTaskName", "Название.",  null,"Война войной, а обед по расписанию", TEXT, false),
  USER_TASK_TYPE("userTaskType", "Тип.",  null, "Напоминалка", SELECT, false),
  USER_TASK_PERIOD("userTaskPeriod", "Повторяющийся?.",  null, "Каждую пятницу", SELECT, false),
  USER_TASK_EXECUTION_DATE("userTaskExecutionDate", "Дата выполнения (первое срабатывание).",  LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),"ДД.MM.ГГГГ", TEXT, false),
  USER_TASK_EXECUTION_TIME("userTaskExecutionTime", "Время выполнения.", LocalTime.now().plusMinutes(15).format(DateTimeFormatter.ofPattern("HH:mm")), "hh:mm", TEXT, false),
  USER_TASK_MESSAGE("userTaskMessage", "Послание в будущее.",  null,"Пора бы и отпотчевать!", TEXT, true),
  USER_GROUP_VALUE("userGroups", "Нотификация во всю группу? Легко!",  null,"", SELECT, true),

  ;

  public static final List<MattermostDialogElementValue> MM_REGISTRATION_VALUES = List.of(REGISTRATION_FIRST_NAME,
      REGISTRATION_LAST_NAME, REGISTRATION_MIDDLE_NAME, REGISTRATION_BIRTHDATE, REGISTRATION_POSITION,
      REGISTRATION_PHONE_NUMBER, REGISTRATION_EMAIL);

  public static final List<MattermostDialogElementValue> MM_CREATE_GROUP_VALUES = List.of(GROUP_NAME,
      GROUP_CHANNEL_ID, GROUP_EMAIL, GROUP_DESCRIPTION);

  public static final Map<String, BiConsumer<UserRegistrationDto, String>> MM_USER_REGISTRATION_MAPPING =
      Map.of(REGISTRATION_FIRST_NAME.getName(), (UserRegistrationDto::setFirstName),
          REGISTRATION_LAST_NAME.getName(), (UserRegistrationDto::setLastName),
          REGISTRATION_MIDDLE_NAME.getName(), (UserRegistrationDto::setMiddleName),
          REGISTRATION_BIRTHDATE.getName(), (UserRegistrationDto::setBirthday),
          REGISTRATION_PHONE_NUMBER.getName(), (UserRegistrationDto::setPhone),
          REGISTRATION_EMAIL.getName(), (UserRegistrationDto::setEmail),
          REGISTRATION_POSITION.getName(), ((dto, value) -> dto.setPosition(Position.valueOf(value)))
      );

  public static final Map<String, BiConsumer<UserPointDto, String>> MM_USER_POINTS_CREATE =
      Map.of(
          USER_POINTS_TYPES.getName(), ((userPointDto, pointType) -> {
            userPointDto.setType(PointType.valueOf(pointType));
          }),
          USER_POINTS_OPEN_QUESTIONS.getName(), ((userPointDto, openQuestions) -> {
            if (StringUtils.isNotBlank(openQuestions)) {
              userPointDto.setOpenQuestions(Set.of(openQuestions.split("\n")));
            }
          })
      );

  public static final Map<String, BiConsumer<CreateGroupDto, String>> MM_CREATE_GROUP_MAPPING =
      Map.of(GROUP_NAME.getName(), (CreateGroupDto::setName),
          GROUP_CHANNEL_ID.getName(), (CreateGroupDto::setChannelId),
          GROUP_EMAIL.getName(), (CreateGroupDto::setEmail),
          GROUP_DESCRIPTION.getName(), (CreateGroupDto::setDescription));

  public static final Map<String, BiConsumer<AddUserToGroupDto, String>> MM_ADD_USER_TO_GROUP_MAPPING =
      Map.of(AVAILABLE_GROUPS_BY_USER.getName(), ((dto, value) -> dto.setGroupId(Long.valueOf(value))),
          AVAILABLE_USERS_FOR_ADD_TO_GROUP.getName(), ((dto, value) -> dto.setUserId(Long.valueOf(value))));

  public static final Map<String, BiConsumer<CreateTaskDto, String>> MM_CREATE_USER_TASK_MAPPING =
      Map.of(USER_TASK_NAME.getName(), (CreateTaskDto::setName),
          USER_TASK_TYPE.getName(), ((dto, value) -> dto.setType(TaskType.valueOf(value))),
          USER_TASK_PERIOD.getName(), ((dto, value) -> dto.setPeriod(TaskPeriod.valueOf(value))),
          USER_TASK_EXECUTION_DATE.getName(), ((dto, value) -> dto.setExecutionDate(LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy")))),
          USER_TASK_EXECUTION_TIME.getName(), ((dto, value) -> dto.setExecutionTime(LocalTime.parse(value, DateTimeFormatter.ofPattern("HH:mm")))),
          USER_TASK_MESSAGE.getName(), (CreateTaskDto::setMessage),
          USER_GROUP_VALUE.getName(), ((dto, value) -> {
            if (Objects.nonNull(value)) {
              dto.setGroupIdForNotify(Long.valueOf(value));
            }
          })
      );

  private final String name;
  private final String defaultName;
  private final String defaultText;
  private final String placeholder;
  private final MattermostDialogElementType elementType;
  private final boolean optional;
}
