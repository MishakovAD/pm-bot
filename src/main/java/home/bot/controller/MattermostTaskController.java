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
public class MattermostTaskController {

  //todo: удалить задание реализовать
  public static final String MATTERMOST_USER_TASK_CREATE_START = "/mattermost/tasks/create/start";
  public static final String MATTERMOST_USER_TASK_CREATE_FINISH = "/mattermost/tasks/create/finish";

  public static final String MATTERMOST_USER_TASK_REMOVE_START = "/mattermost/tasks/remove/start";
  public static final String MATTERMOST_USER_TASK_REMOVE_FINISH = "/mattermost/tasks/remove/finish";

  private final EventHandlerService<Object, ? extends BaseMattermostRequest> eventHandlerService;

  @PostMapping(MATTERMOST_USER_TASK_CREATE_START)
  public Object startUserTaskCreate(@RequestBody PressMattermostButtonRequest request) {
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_USER_TASK_CREATE_START, request);
  }

  @PostMapping(MATTERMOST_USER_TASK_CREATE_FINISH)
  public Object finishUserTaskCreate(@RequestBody BaseMattermostSubmitDialogRequest request) {
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_USER_TASK_CREATE_FINISH, request);
  }

  @PostMapping(MATTERMOST_USER_TASK_REMOVE_START)
  public Object startUserTaskRemove(@RequestBody PressMattermostButtonRequest request) {
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_USER_TASK_REMOVE_START, request);
  }

  @PostMapping(MATTERMOST_USER_TASK_REMOVE_FINISH)
  public Object finishUserTaskRemove(@RequestBody BaseMattermostSubmitDialogRequest request) {
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_USER_TASK_REMOVE_FINISH, request);
  }

}
