package com.softz.identity.dto.request;

import com.softz.identity.validator.DobConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NewUserRequest {
    @Size(min = 3, max = 30, message = "INVALID_USERNAME")
    @NotNull
    private String username;
    private String password;

    @DobConstraint(min = 18, message = "INVALID_DATE_OF_BIRTH")
    private LocalDate dob;
}
