package home.bot.service;

import home.bot.entity.User;
import java.util.List;

//Сервис для поиска пользователя из разных систем (разные способы записи "логина")
public interface UserService {

  List<User> getAllUsersForAddToGroup(String userAdminAddress);
  User getUserById(Long userId);
  User getUserByAddress(String userAddress);

  boolean isUserExists(String userAddress);
}
