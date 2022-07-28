package home.bot.service;

import home.bot.dto.UserRegistrationDto;
import home.bot.entity.User;

public interface UserRegistrationService {
  User createUser(UserRegistrationDto userRegistrationDto);
}
