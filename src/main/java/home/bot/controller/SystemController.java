package home.bot.controller;

import home.bot.dto.enums.Role;
import home.bot.dto.response.BaseResponse;
import home.bot.service.UserRoleService;
import home.bot.service.mattermost.MattermostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SystemController {

  private final UserRoleService userRoleService;
  private final MattermostService mattermostService;

  @GetMapping("/system/user/role/add")
  public Object addRoleToUser(@RequestParam Long userId, @RequestParam Role role) {
    userRoleService.addRoleToUser(userId, role);
    return new BaseResponse();
  }

  @GetMapping("/system/bot/channel/send")
  public Object sendMessageToGroup(@RequestParam String address, @RequestParam String message) {
    mattermostService.sendMessageFromBotToGroup(address, message);
    return new BaseResponse();
  }

}
