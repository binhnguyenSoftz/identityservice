package com.softz.identity.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePermissionRequest {
    @NotNull
    private String id;

    @NotNull
    private String name;
    private String description;
}
