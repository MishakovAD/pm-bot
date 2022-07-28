package home.bot.service.mattermost;

import home.bot.dto.enums.MattermostBotMenuButton;
import home.bot.dto.enums.MattermostDialogElementValue;
import home.bot.dto.matermost.MattermostDialogElement;
import java.util.Map;
import net.bis5.mattermost.model.SlackAttachment;
import net.bis5.mattermost.model.SlackAttachmentField;

public interface MattermostElementBuilder {

  MattermostDialogElement createDefaultDialogElement(MattermostDialogElementValue value);

  MattermostDialogElement createSelectDialogElement(MattermostDialogElementValue value, Map<String, String> params);

  SlackAttachment createAttachment(MattermostBotMenuButton button, String pretext);
  SlackAttachment createAttachment(MattermostBotMenuButton button);

  SlackAttachmentField createAttachmentField(String title, Object value, boolean shortField);
}
