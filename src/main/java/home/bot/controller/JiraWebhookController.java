package home.bot.controller;

import home.bot.dto.enums.EventSource;
import home.bot.dto.jira.JiraEvent;
import home.bot.dto.response.BaseResponse;
import home.bot.service.EventHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JiraWebhookController {

  private final EventHandlerService<JiraEvent, Object> eventHandlerService;

  @PostMapping("/rest/webhooks/jira")
  public Object onEvent(@RequestBody JiraEvent event) {
    eventHandlerService.handleEvent(EventSource.JIRA, event);
    return new BaseResponse();
  }

}
