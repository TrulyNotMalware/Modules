package dev.notypie.application.command;

import java.util.List;
import java.util.Map;

public interface CommandService {

    void parseCommandRequest(Map<String, List<String>> headers, Map<String, Object> payload);
}