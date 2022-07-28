package home.bot.controller;

import home.bot.dto.enums.EventSource;
import home.bot.dto.matermost.request.BaseMattermostRequest;
import home.bot.dto.matermost.request.BaseMattermostSubmitDialogRequest;
import home.bot.dto.matermost.request.PressMattermostButtonRequest;
import home.bot.dto.matermost.request.SlashCommandRequest;
import home.bot.dto.matermost.request.WebhookRequest;
import home.bot.service.EventHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MattermostWebhookController {

  public static final String MATTERMOST_WEBHOOK = "/mattermost/webhook";
  public static final String MATTERMOST_SLASH_COMMAND = "/mattermost/slash";

  private final EventHandlerService<Object, ? extends BaseMattermostRequest> eventHandlerService;

  @PostMapping(MATTERMOST_WEBHOOK)
  public Object mattermostWebhook(@RequestBody WebhookRequest request) {
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_WEBHOOK, request);
  }

  @GetMapping(MATTERMOST_SLASH_COMMAND)
  public Object mattermostSlashCommand(SlashCommandRequest request) {
    //get Daily Points - все открытые вопросы
    //comment/edit(??) daily points
    //create reminder
    //get reminders
    //remove reminder
    //start random daily ведущий
    return eventHandlerService.handleEvent(EventSource.MATTERMOST_SLASH_COMMAND, request);
  }
}
