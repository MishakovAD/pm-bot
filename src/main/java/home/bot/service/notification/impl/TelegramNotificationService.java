package home.bot.service.notification.impl;

import home.bot.dto.Message;
import home.bot.repository.NotificationAddressRepository;
import home.bot.service.NotificationAddressService;
import home.bot.service.notification.AbstractNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("telegramNotifyHandler")
@Slf4j(topic = "NOTIFICATION_SERVICE.TELEGRAM")
public class TelegramNotificationService extends AbstractNotificationService {

  private RestTemplate telegramRestTemplate;

  public TelegramNotificationService(NotificationAddressService notificationAddressService,
      @Qualifier("telegramRestTemplate") RestTemplate telegramRestTemplate) {
    super(notificationAddressService);
    this.telegramRestTemplate = telegramRestTemplate;
  }


  @Override
  public void notify(Message message) {
    LOGGER.info("Send telegram message: {}", message);
//    sendSimpleMessage(chatId, message.getMessage());
  }

  private void sendSimpleMessage(String chatId, String text) {
    telegramRestTemplate.getForEntity(createSendMessageUri(chatId, text), Object.class);
  }

  private String createSendMessageUri(String chatId, String text) {
    return String.format("/sendMessage?chat_id=%s&text=%s", chatId, text);
  }

  //  //TODO: сделать UriBuilder, чтобы он мог нормально передавать русский текст
//  private void sendSimpleMessage(String chatId, String text) {
//    URIBuilder uriBuilder = new URIBuilder()
//        .setPath("/sendMessage")
//        .addParameter("chat_id", chatId)
//        .addParameter("text", text);
//    telegramRestTemplate.getForEntity(uriBuilder.toString(), Object.class);
//  }
}
