package com.softz.identity.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRequest {
    private String username;
    private String password;
}
