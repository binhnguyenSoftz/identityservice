package com.softz.identity.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPermissionRequest {
    @NotNull
    private String name;
    private String description;
}
