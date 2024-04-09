package dev.notypie.aggregate.commands.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public abstract class CommandContext {
    private final Map<String, List<String>> headers;
    private final Map<String, Object> payload;

    void addHeader(String key, String data){
        if (headers.containsKey(key)) {
            headers.get(key).add(data);
        } else {
            headers.put(key, new ArrayList<>(Collections.singletonList(data)));
        }
    }

    public CommandContext(){
        this.headers = new ConcurrentHashMap<>();
        this.payload = new ConcurrentHashMap<>();
    }

    public CommandContext(Map<String, List<String>> headers, Map<String, Object> payload){
        this.headers = headers;
        this.payload = payload;
    }

    public CommandContext(Map<String, Object> payload){
        this.headers = new ConcurrentHashMap<>();
        this.payload = payload;
    }

    public abstract void executeCommand();
    public abstract void validateCommand();
}