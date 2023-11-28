package dev.notypie.aggregate.slack;

import com.slack.api.bolt.App;
import com.slack.api.bolt.response.Response;
import com.slack.api.model.event.AppMentionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@Profile("slack-bolt")
public class SlackBoltConfig {

    @Value("${slack.app.token}")
    private String token;

    @Bean
    //We do not support socketmode in springframework.
    public App startSocketModeApp() throws Exception {
        log.info("Start Slack bolt service.");
        App app = new App();
        app.command("hello", (slashCommandRequest, context) -> context.ack("this is test"));
        app.event(AppMentionEvent.class, (event, context) -> {
            log.info("event = {}, context = {}",event, context);
            return Response.ok();
        });
        return app;
    }
}
