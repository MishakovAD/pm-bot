package home.bot.controller;

import home.bot.dto.enums.EventSource;
import home.bot.dto.matermost.request.BaseMattermostRequest;
import home.bot.dto.matermost.request.BaseMattermostSubmitDialogRequest;
import home.bot.dto.matermost.request.PressMattermostButtonRequest;
import home.bot.service.EventHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MattermostPointController {
  public static final String MATTERMOST_USER_POINTS_START = "/mattermost/points/start";
  public static final String MATTERMOST_CREATE_USER_POINTS = "/mattermost/points/create";
  public static final String MATTERMOST_USER_POINTS_DONE_START = "/mattermost/points/done/start";
  public static final String MATTERMOST_USER_POINTS_DONE_FINISH = "/mattermost/points/done/finish";
  public static final String MATTERMOST_ADD_COMMENT_TO_QUESTION_START = "/mattermost/points/comment/start";
  public static final String MATTERMOST_ADD_COMMENT_TO_QUESTION_FINISH = "/mattermost/points/comment/add";

  private final EventHandlerService<Object, ? extends BaseMattermostRequest> eventHandlerService;

  @PostMapping(MATTERMOST_USER_POINTS_START)
  public Object startDailyPoints(@RequestBody PressMattermostButtonRequest request) {
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_USER_POINT_CREATE_START, request);
  }

  @PostMapping(MATTERMOST_CREATE_USER_POINTS)
  public Object createDailyPoints(@RequestBody BaseMattermostSubmitDialogRequest request) {
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_USER_POINT_CREATE_FINISH, request);
  }

  @PostMapping(MATTERMOST_USER_POINTS_DONE_START)
  public Object startDoneDailyPoints(@RequestBody PressMattermostButtonRequest request) {
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_USER_QUESTION_DONE_START, request);
  }

  @PostMapping(MATTERMOST_USER_POINTS_DONE_FINISH)
  public Object doneDailyPoints(@RequestBody BaseMattermostSubmitDialogRequest request) {
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_USER_QUESTION_DONE_FINISH, request);
  }

  @PostMapping(MATTERMOST_ADD_COMMENT_TO_QUESTION_START)
  public Object startAddCommentsToQuestion(@RequestBody PressMattermostButtonRequest request) {
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_ADD_COMMENT_TO_QUESTION_START, request);
  }

  @PostMapping(MATTERMOST_ADD_COMMENT_TO_QUESTION_FINISH)
  public Object addCommentsToQuestion(@RequestBody BaseMattermostSubmitDialogRequest request) {
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_ADD_COMMENT_TO_QUESTION_FINISH, request);
  }

}
