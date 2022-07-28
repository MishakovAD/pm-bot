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
public class MattermostUserController {
  public static final String MATTERMOST_USER_REGISTRATION = "/mattermost/user/registration";
  public static final String MATTERMOST_USER_POINTS_TYPES = "/mattermost/user/points/types";
  public static final String MATTERMOST_USER_CURRENT_POINTS = "/mattermost/user/points/find";

  private final EventHandlerService<Object, ? extends BaseMattermostRequest> eventHandlerService;

  @PostMapping(MATTERMOST_USER_REGISTRATION)
  public Object mattermostUserRegistration(@RequestBody BaseMattermostSubmitDialogRequest request) {
    System.out.println(request);
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_USER_REGISTRATION, request);
  }

  @PostMapping(MATTERMOST_USER_POINTS_TYPES)
  public Object getUserPointsTypes(@RequestBody PressMattermostButtonRequest request) {
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_USER_POINTS_TYPES, request);
  }

  @PostMapping(MATTERMOST_USER_CURRENT_POINTS)
  public Object findCurrentUserPoints(@RequestBody PressMattermostButtonRequest request) {
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_USER_CURRENT_POINTS, request);
  }

}
