package dev.notypie.aggregate.slack.commands;

import dev.notypie.aggregate.slack.dto.*;
import dev.notypie.aggregate.slack.dto.contexts.SlackAppMentionContext;
import dev.notypie.constants.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

@Slf4j
@Getter
public class AppMentionCommand implements Command{

    private Command command;
    private String botId;
    private final String channel;
    private final String userId;
    private final String rawText;

    @Builder
    public AppMentionCommand(SlackAppMentionContext context, String channel){
        AppMentionEventType event = context.getEvent();
        this.userId = event.getUserId();
        this.rawText = event.getRawText();
        this.channel = channel;
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

    // Special Case, app mention event
    private void parseCommand(List<Element> elementList){
        Queue<String> userQueue = new LinkedList<>();
        Queue<String> commandQueue = new LinkedList<>();

        for(Element element: elementList){
            // Skip bot user.
            if(element.getType().equals(Constants.ELEMENT_TYPE_USER) && element.getUserId().equals(this.botId)) continue;
            if(element.getType().equals(Constants.ELEMENT_TYPE_USER))
                userQueue.offer(element.getUserId());
            else if(element.getType().equals(Constants.ELEMENT_TYPE_TEXT)){
                StringTokenizer tokenizer = new StringTokenizer(element.getText(),Constants.COMMAND_DELIMITER);
                while(tokenizer.hasMoreTokens()) commandQueue.offer(tokenizer.nextToken());
//                commandQueue.offer(element.getText());
            }
        }
        //Construct Command
        this.buildCommand(userQueue, commandQueue);
    }

    private void buildCommand(Queue<String> userQueue, Queue<String> commandQueue){
        if(commandQueue.isEmpty()) {
//            ArgumentError error = new ArgumentError("Command", "null","Command is empty.");
//            throw new SlackDomainException(SlackErrorCodeImpl.NOT_A_VALID_REQUEST, );
        }
        else{//Command is not empty
            String command = commandQueue.poll().replaceAll(" ","");//remove whitespace
            switch(command) {
                case Constants.NOTICE_COMMAND -> this.command = NoticeCommand.builder().users(userQueue).channel(this.channel)
                        .textQueue(commandQueue).build();
                case Constants.ALERT_COMMAND -> this.command = NoticeCommand.builder().users(userQueue).channel(this.channel).build();
                default -> this.command = ResponseTextCommand.builder().body("command "+command+" not found").channel(this.channel).build();
            }
        }
    }

    @Override
    public String toStringCommand() {
        return this.command.toStringCommand();
    }

    //call Generate command contents
    @Override
    public SlackEventContents generateEventContents() {
        return this.command.generateEventContents();
    }
}
