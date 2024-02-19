package dev.notypie.global.configurations;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.response.Response;
import com.slack.api.model.event.AppMentionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Deprecated
//@Configuration
//@Profile("slack-bolt")
public class SlackBoltConfig {

    @Value("${slack.app.token}")
    private String token;
    @Value("${slack.api.token}")
    private String botToken;
    @Value("${slack.api.signingSecret}")
    private String signingSecret;

//    @Bean
    public AppConfig appConfig(){
        return AppConfig.builder()
                .singleTeamBotToken(this.botToken)
                .signingSecret(this.signingSecret)
                .build();
    }

//    @Bean
    //We do not support socketmode in springframework.
    public App startSocketModeApp(AppConfig appConfig) throws Exception {
        log.info("Start Slack bolt service.");
        App app = new App(appConfig);

        if (appConfig.getClientId() != null) {
            app.asOAuthApp(true);
        }
        
        app.command("hello", (slashCommandRequest, context) -> context.ack("this is test"));
        app.event(AppMentionEvent.class, (event, context) -> {
            log.info("event = {}, context = {}",event, context);
            return Response.ok();
        });
        return app;
    }
}
