package home.bot.service.mattermost.impl;

import static home.bot.dto.enums.MattermostDialogElementType.SELECT;

import home.bot.dto.enums.MattermostBotMenuButton;
import home.bot.dto.enums.MattermostDialogElementType;
import home.bot.dto.enums.MattermostDialogElementValue;
import home.bot.dto.matermost.MattermostDialogElement;
import home.bot.dto.matermost.MattermostElementOption;
import home.bot.service.mattermost.MattermostElementBuilder;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bis5.mattermost.model.PostAction;
import net.bis5.mattermost.model.PostAction.PostActionIntegration;
import net.bis5.mattermost.model.SlackAttachment;
import net.bis5.mattermost.model.SlackAttachmentField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class MattermostElementBuilderImpl implements MattermostElementBuilder {

  @Value("${mattermost.response-host}")
  private String RESPONSE_HOST;

  @Override
  public MattermostDialogElement createDefaultDialogElement(MattermostDialogElementValue value) {
    return MattermostDialogElement.builder()
        .name(value.getName())
        .displayName(value.getDefaultName())
        .defaultText(value.getDefaultText())
        .placeholder(value.getPlaceholder())
        .type(value.getElementType().getType())
        .optional(value.isOptional())
        .build();
  }

  @Override
  public MattermostDialogElement createSelectDialogElement(MattermostDialogElementValue value, Map<String, String> params) {
    MattermostDialogElementType elementType = value.getElementType();
    if (!SELECT.equals(elementType)) {
      return createDefaultDialogElement(value);
    }
    return MattermostDialogElement.builder()
        .name(value.getName())
        .displayName(value.getDefaultName())
        .defaultText(value.getDefaultText())
        .placeholder(value.getPlaceholder())
        .type(SELECT.getType())
        .optional(value.isOptional())
        .options(createElementOptions(params))
        .build();
  }

  @Override
  public SlackAttachment createAttachment(MattermostBotMenuButton button, String pretext) {
    SlackAttachment attachment = createAttachment(button);
    attachment.setPretext(pretext);
    return attachment;
  }

  @Override
  public SlackAttachment createAttachment(MattermostBotMenuButton button) {
    SlackAttachment attachment = new SlackAttachment();
    attachment.setActions(createActions(button));
    return attachment;
  }

  @Override
  public SlackAttachmentField createAttachmentField(String title, Object value, boolean shortField) {
    SlackAttachmentField field = new SlackAttachmentField();
    field.setTitle(title);
    field.setValue(value);
    field.setShortField(shortField);
    return field;
  }

  private List<PostAction> createActions(MattermostBotMenuButton button) {
    PostAction action = new PostAction();
    action.setId(button.getId());
    action.setName(button.getName());
    PostActionIntegration actionIntegration = new PostActionIntegration();
    actionIntegration.setUrl(RESPONSE_HOST + button.getUrl());
    actionIntegration.setContext(button.getContext());
    action.setIntegration(actionIntegration);
    return List.of(action);
  }

  private List<MattermostElementOption> createElementOptions(Map<String, String> params) {
    return params.entrySet()
        .stream()
        .map(entry -> new MattermostElementOption(entry.getKey(), entry.getValue()))
        .toList();
  }

}
