package com.softz.identity.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDto {
    private String userId;
    private String username;
    private LocalDate dob;
}
