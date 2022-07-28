package home.bot.config.mattermost;

import home.bot.dto.matermost.request.OpenDialogRequest;
import net.bis5.mattermost.client4.api.AuditsApi;
import net.bis5.mattermost.client4.api.AuthenticationApi;
import net.bis5.mattermost.client4.api.BotsApi;
import net.bis5.mattermost.client4.api.BrandApi;
import net.bis5.mattermost.client4.api.ChannelApi;
import net.bis5.mattermost.client4.api.ClusterApi;
import net.bis5.mattermost.client4.api.CommandsApi;
import net.bis5.mattermost.client4.api.ComplianceApi;
import net.bis5.mattermost.client4.api.ElasticsearchApi;
import net.bis5.mattermost.client4.api.EmojiApi;
import net.bis5.mattermost.client4.api.FilesApi;
import net.bis5.mattermost.client4.api.LdapApi;
import net.bis5.mattermost.client4.api.LogsApi;
import net.bis5.mattermost.client4.api.OAuthApi;
import net.bis5.mattermost.client4.api.OpenGraphApi;
import net.bis5.mattermost.client4.api.PluginApi;
import net.bis5.mattermost.client4.api.PostApi;
import net.bis5.mattermost.client4.api.PreferencesApi;
import net.bis5.mattermost.client4.api.ReactionApi;
import net.bis5.mattermost.client4.api.SamlApi;
import net.bis5.mattermost.client4.api.StatusApi;
import net.bis5.mattermost.client4.api.SystemApi;
import net.bis5.mattermost.client4.api.TeamApi;
import net.bis5.mattermost.client4.api.UserApi;
import net.bis5.mattermost.client4.api.WebhookApi;

public interface InteractiveMattermostClient extends AutoCloseable, AuditsApi, AuthenticationApi, BotsApi,
    BrandApi, ChannelApi, ClusterApi, CommandsApi, ComplianceApi, ElasticsearchApi, EmojiApi,
    FilesApi, SystemApi, LdapApi, LogsApi, OAuthApi, OpenGraphApi, PluginApi, PostApi,
    PreferencesApi, ReactionApi, SamlApi, StatusApi, TeamApi, UserApi, WebhookApi {

  void openDialog(OpenDialogRequest request);

}
