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
public class MattermostAdminCommandController {
  public static final String MATTERMOST_ADMIN_CREATE_GROUP_START = "/mattermost/admin/group/create/start";
  public static final String MATTERMOST_ADMIN_CREATE_GROUP_FINISH = "/mattermost/admin/group/create/finish";
  public static final String MATTERMOST_ADMIN_ADD_USER_TO_GROUP_START = "/mattermost/admin/group/add/user/start";
  public static final String MATTERMOST_ADMIN_ADD_USER_TO_GROUP_FINISH = "/mattermost/admin/group/add/user/finish";

  private final EventHandlerService<Object, ? extends BaseMattermostRequest> eventHandlerService;

  @PostMapping(MATTERMOST_ADMIN_CREATE_GROUP_START)
  public Object createGroupStart(@RequestBody PressMattermostButtonRequest request) {
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_ADMIN_CREATE_GROUP_START, request);
  }

  @PostMapping(MATTERMOST_ADMIN_CREATE_GROUP_FINISH)
  public Object createGroupFinish(@RequestBody BaseMattermostSubmitDialogRequest request) {
    System.out.println(request);
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_ADMIN_CREATE_GROUP_FINISH, request);
  }

  @PostMapping(MATTERMOST_ADMIN_ADD_USER_TO_GROUP_START)
  public Object addUserToGroupStart(@RequestBody PressMattermostButtonRequest request) {
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_ADMIN_ADD_USER_TO_GROUP_START, request);
  }

  @PostMapping(MATTERMOST_ADMIN_ADD_USER_TO_GROUP_FINISH)
  public Object addUserToGroupFinish(@RequestBody BaseMattermostSubmitDialogRequest request) {
    System.out.println(request);
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_ADMIN_ADD_USER_TO_GROUP_FINISH, request);
  }
}
