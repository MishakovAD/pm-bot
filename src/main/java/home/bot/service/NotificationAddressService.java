package home.bot.service;

import home.bot.dto.enums.NotificationSource;
import home.bot.dto.matermost.MattermostNotifyAddressDto;
import home.bot.entity.NotificationAddress;

public interface NotificationAddressService {

  void addMattermostUserAddresses(Long userId, MattermostNotifyAddressDto addressDto);
  NotificationAddress addMattermostChannelId(String channelId);

  NotificationAddress getAddressByUser(Long userId, NotificationSource source);

  NotificationAddress getAddressByGroup(Long groupId, NotificationSource source);

  NotificationAddress getAddressById(Long addressId);
}
