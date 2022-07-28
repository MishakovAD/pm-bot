package home.bot.service;

import home.bot.dto.UserPointDto;
import home.bot.dto.enums.PointType;
import home.bot.entity.Point;

public interface PointsService {

  String getTodayDailyPointsText(Long groupId);

  Point createTodayUserPoint(UserPointDto userPointDto);

  String createMessageForOpenUserQuestions(String userAddress, PointType pointType, String title);
}
