package home.bot.service.impl;

import home.bot.dto.UserRegistrationDto;
import home.bot.entity.User;
import home.bot.repository.UserRepository;
import home.bot.service.TaskService;
import home.bot.service.UserRegistrationService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class UserRegistrationServiceImpl implements UserRegistrationService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public User createUser(UserRegistrationDto userRegistrationDto) {
    User user = new User();
    user.setFirstName(userRegistrationDto.getFirstName());
    user.setMiddleName(userRegistrationDto.getMiddleName());
    user.setLastName(userRegistrationDto.getLastName());
    user.setBirthday(LocalDate.parse(userRegistrationDto.getBirthday(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    user.setPosition(userRegistrationDto.getPosition());

    return userRepository.save(user);
  }
}
