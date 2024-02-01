package dev.notypie.controllers;

import com.slack.api.bolt.App;
import com.slack.api.bolt.jakarta_servlet.SlackAppServlet;

@Deprecated
//@Profile("slack-bolt")
//@WebServlet("/slack/events")
public class SlackBoltController extends SlackAppServlet {
    public SlackBoltController(App app) {
        super(app);
    }
}