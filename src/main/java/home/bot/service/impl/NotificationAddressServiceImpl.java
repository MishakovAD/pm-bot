package home.bot.service.impl;

import home.bot.dto.enums.NotificationSource;
import home.bot.dto.matermost.MattermostNotifyAddressDto;
import home.bot.entity.NotificationAddress;
import home.bot.entity.User;
import home.bot.repository.NotificationAddressRepository;
import home.bot.repository.UserGroupRepository;
import home.bot.repository.UserRepository;
import home.bot.service.NotificationAddressService;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class NotificationAddressServiceImpl implements NotificationAddressService {

  private final UserRepository userRepository;
  private final UserGroupRepository userGroupRepository;
  private final NotificationAddressRepository notificationAddressRepository;

  @Override
  @Transactional
  public void addMattermostUserAddresses(Long userId, MattermostNotifyAddressDto addressDto) {
    User user = userRepository.getById(userId);
    NotificationAddress email = createEmailAddress(addressDto.getEmail());
    NotificationAddress phone = createPhoneAddress(addressDto.getPhone());
    NotificationAddress mmUserId = createMMUserIdAddress(addressDto.getChatId());
    NotificationAddress mmDirectId = createMMDirectIdAddress(addressDto.getDialogId());
    user.getNotificationAddresses().addAll(Set.of(email, phone, mmUserId, mmDirectId));
    userRepository.save(user);
  }

  @Override
  @Transactional
  public NotificationAddress addMattermostChannelId(String channelId) {
    NotificationAddress address = createNotificationAddress(channelId, NotificationSource.MATTERMOST_CHANEL_ID);
    address.setEnable(Boolean.TRUE);
    return notificationAddressRepository.save(address);
  }

  @Override
  @Transactional
  public NotificationAddress getAddressByUser(Long userId, NotificationSource source) {
    return userRepository.getById(userId)
        .getNotificationAddresses()
        .stream()
        .filter(address -> Objects.equals(address.getSource(), source))
        .findAny()
        .orElseThrow(() -> new RuntimeException(String.format("Address for user: %s not found!", userId)));
  }

  @Override
  public NotificationAddress getAddressByGroup(Long groupId, NotificationSource source) {
    return userGroupRepository.getById(groupId).getAddress();
  }

  @Override
  @Transactional
  public NotificationAddress getAddressById(Long addressId) {
    return notificationAddressRepository.findById(addressId)
        .orElseThrow(() -> new RuntimeException(String.format("Address with id: %s not found!", addressId)));
  }

  private NotificationAddress createEmailAddress(String email) {
    return createNotificationAddress(email, NotificationSource.EMAIL);
  }

  private NotificationAddress createPhoneAddress(String phone) {
    return createNotificationAddress(phone, NotificationSource.PHONE);
  }

  private NotificationAddress createMMUserIdAddress(String userId) {
    return createNotificationAddress(userId, NotificationSource.MATTERMOST_USER_ID);
  }

  private NotificationAddress createMMDirectIdAddress(String directId) {
    return createNotificationAddress(directId, NotificationSource.MATTERMOST_DIRECT_ID);
  }

  private NotificationAddress createNotificationAddress(String value, NotificationSource source) {
    NotificationAddress address = new NotificationAddress();
    address.setAddress(value);
    address.setSource(source);
    return address;
  }
}
