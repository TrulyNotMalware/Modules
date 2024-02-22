package dev.notypie.application.command;

import dev.notypie.aggregate.app.entity.App;
import dev.notypie.aggregate.app.repository.AppRepository;
import dev.notypie.aggregate.commands.entity.Command;
import dev.notypie.aggregate.commands.entity.CommandBuilder;
import dev.notypie.aggregate.commands.entity.CommandContext;
import dev.notypie.aggregate.commands.entity.PayloadKeyNames;
import dev.notypie.global.error.exceptions.CommandErrorCodeImpl;
import dev.notypie.global.error.exceptions.CommandException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService{

    private final AppRepository appRepository;
    private final List<CommandBuilder> commandBuilders;

    @Override
    public void parseCommandRequest(Map<String, List<String>> headers, Map<String, Object> payload) {

        String appId = resolveAppId(payload);
        App app = this.appRepository.findByAppId(appId);

        for( CommandBuilder builder : this.commandBuilders){
            if( builder.isSupport(app.getAppType()) ){
                Command command = builder.buildCommand(app.getAppId(), headers, payload);
                builder.executeCommand(command);
            }
        }
    }

    private String resolveAppId(Map<String, Object> payload){
        // Iterate over each value in the AppCheckKeyNames enum
        for (PayloadKeyNames key : PayloadKeyNames.values()) {
            // If payload contains the enum's keyName
            if (payload.containsKey(key.getKeyName())) {
                return payload.get(key.getKeyName()).toString();
            }
        }
        throw new CommandException(CommandErrorCodeImpl.COMMAND_TYPE_NOT_DETECTED);
    }
}
