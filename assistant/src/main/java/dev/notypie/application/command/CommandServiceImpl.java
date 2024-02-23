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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService{

    private final AppRepository appRepository;
    private final List<CommandBuilder> commandBuilders;

    @Override
    public ResponseEntity<?> executeCommand(Map<String, List<String>> headers, Map<String, Object> payload) {

        String appId = resolveAppId(payload);
        App app = this.appRepository.findByAppId(appId);

        return this.commandBuilders.stream()
                .filter(commandBuilder -> commandBuilder.isSupport(app.getAppType()))
                .findFirst()
                .map(selectedBuilder -> {
                    Command command = selectedBuilder.buildCommand(app.getAppId(), headers, payload);
                    return selectedBuilder.executeCommand(command);
                })
                .orElseThrow(() -> new CommandException(CommandErrorCodeImpl.UNKNOWN_COMMAND_TYPE));
//        for( CommandBuilder builder : this.commandBuilders){
//            if( builder.isSupport(app.getAppType()) ){
//                Command command = builder.buildCommand(app.getAppId(), headers, payload);
//                return builder.executeCommand(command);
//            }
//        }
//        throw new CommandException(CommandErrorCodeImpl.UNKNOWN_COMMAND_TYPE);
    }

    private String resolveAppId(Map<String, Object> payload){
        // Iterate over each value in the AppCheckKeyNames enum
        return Arrays.stream(PayloadKeyNames.values())
                .filter(key -> payload.containsKey(key.getKeyName()))
                .findFirst()
                .map(key -> payload.get(key.getKeyName()).toString())
                .orElseThrow(() ->
                        new CommandException(CommandErrorCodeImpl.COMMAND_TYPE_NOT_DETECTED));
    }
}
