package dev.notypie.domain.history.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

class ApplicationInfo {

    private final String appId;
    // publish or subscribe type.
    private final String appName;
    private final String teamName;
    private final String source;

    private final boolean isEnterprise;

    @Builder
    protected ApplicationInfo(@NotNull String appId, @NotNull String appName, @NotNull String teamName,
                    @NotNull String source, boolean isEnterprise){
        this.appId = appId;
        this.appName = appName;
        this.teamName = teamName;
        this.source = source;
        this.isEnterprise = isEnterprise;
    }


}
