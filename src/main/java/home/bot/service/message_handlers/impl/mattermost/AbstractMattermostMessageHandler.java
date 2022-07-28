package home.bot.service.message_handlers.impl.mattermost;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.MessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public abstract class AbstractMattermostMessageHandler<T, Y> implements MessageHandler<T, Y> {

  @Value("${mattermost.response-host}")
  protected String RESPONSE_HOST;

  protected final MattermostService mattermostService;
  protected final InteractiveMattermostClient mattermostClient;
  protected final MattermostElementBuilder mattermostElementBuilder;

}
