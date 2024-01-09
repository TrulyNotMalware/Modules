package dev.notypie.aggregate.slack.commands;

import dev.notypie.aggregate.slack.dto.*;
import dev.notypie.constants.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.StringTokenizer;

@Slf4j
@Getter
public class AppMentionCommand implements Command{

    private Command command;
    private String userId;
    private String botId;
    private String rawText;

    @Builder
    public AppMentionCommand(SlackAppMentionContext context){
        AppMentionEventType event = context.getEvent();
        this.userId = event.getUser();
        this.rawText = event.getText();
        //check Authorization
        for(Authorization auth : context.getAuthorizations()){
            if(auth.isBot()){
                this.botId = auth.getUserId();
                break;
            }
        }

        for(Block block : event.getBlocks()){
            // Block type is text message,
            if(block.getElements().isEmpty()) continue;
            if(block.getType().equals(Constants.BLOCK_TYPE_RICH_TEXT)){
                Element element = block.getElements().removeFirst();
                // is Text type?
                if(element.getType().equals(Constants.ELEMENT_TYPE_TEXT_SECTION))
                    //Parse Data
                    this.parseCommand(element.getElements());

            }
        }
    }

    private void parseCommand(List<Element> elementList){
        for(Element element: elementList){
            if(element.getType().equals(Constants.ELEMENT_TYPE_USER)){
                //EXTRACT USER FROM COMMANDS
            }
        }
    }
    private void parseCommand(String rawText){
//        StringTokenizer tokenizer = new StringTokenizer(rawText, Constants.COMMAND_DELIMITER);
//        if(rawText.isBlank() || rawText.isEmpty() || tokenizer.countTokens() == 0){
//            //is empty or blank then return default command
//            this.command = ResponseTextCommand.builder()
//                    .body("Command not found.").build();
//            return;
//        }
//        String command = tokenizer.nextToken();
//        switch(command) {
//            case Constants.MENTION_COMMAND_ALARM ->
//        }
    }

    @Override
    public String toStringCommand() {
        return this.command.toStringCommand();
    }
}
