package home.bot.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MattermostDialogElementType {
  TEXT("text"),
  TEXTAREA("textarea"),
  SELECT("select"),
  BOOL("bool"),
  RADIO("radio");

  private final String type;
}
