package dev.notypie.global.constants;

public final class Constants {

    // parse requests
    public static final String SLACK_COMMAND_TYPE = "slack_api";
    public static final String REST_COMMAND_TYPE = "rest_api";

    // Slack Request Type Constants
    public static final String URL_VERIFICATION="url_verification";
    public static final String EVENT_CALLBACK = "event_callback";
    public static final String APP_MENTION="app_mention";
    public static final String MESSAGE_EVENT = "message";
    public static final String SLACK_API_ENDPOINT = "https://slack.com/api/";

    // Slack Command Parser
    public static final String COMMAND_DELIMITER = " ";
    public static final String MENTION_HELP_COMMAND = "help";
    public static final String MENTION_COMMAND_CREATE = "create";
    public static final String MENTION_COMMAND_ALARM = "alarm";
    public static final String MENTION_COMMAND_RETURN = "return";
    public static final String BLOCK_TYPE_RICH_TEXT = "rich_text";
    public static final String ELEMENT_TYPE_TEXT_SECTION = "rich_text_section";
    public static final String ELEMENT_TYPE_USER = "user";
    public static final String ELEMENT_TYPE_TEXT = "text";

    // Application Command lists
    public static final String NOTICE_COMMAND = "notice";
    public static final String ALERT_COMMAND = "alert";
}
