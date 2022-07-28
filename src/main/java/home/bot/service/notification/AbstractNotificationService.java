package home.bot.service.notification;

import home.bot.dto.enums.NotificationSource;
import home.bot.entity.NotificationAddress;
import home.bot.entity.User;
import home.bot.exception.UnsupportedNotificationType;
import home.bot.repository.NotificationAddressRepository;
import home.bot.repository.UserRepository;
import home.bot.service.NotificationAddressService;
import java.util.Collection;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public abstract class AbstractNotificationService implements NotificationService {
  protected final NotificationAddressService notificationAddressService;
}
