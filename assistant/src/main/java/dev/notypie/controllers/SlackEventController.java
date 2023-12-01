package dev.notypie.controllers;

import com.slack.api.bolt.App;
import com.slack.api.bolt.jakarta_servlet.SlackAppServlet;
import jakarta.servlet.annotation.WebServlet;
import org.springframework.context.annotation.Profile;

@Profile("slack-bolt")
@WebServlet("/slack/events")
public class SlackEventController extends SlackAppServlet {
    public SlackEventController(App app) {
        super(app);
    }
}