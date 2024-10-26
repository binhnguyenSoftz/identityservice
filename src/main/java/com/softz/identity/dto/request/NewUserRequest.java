package com.softz.identity.dto.request;

import com.softz.identity.validator.DobConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class NewUserRequest {
    @Size(min = 3, max = 10, message = "INVALID_USERNAME")
    private String username;
    private String password;

    @Email(message = "INVALID_EMAIL_ADDRESS")
    private String email;

    @DobConstraint(min = 18, message = "INVALID_DATE_OF_BIRTH")
    private LocalDate dob;

    @Size(min = 1, message = "ROLE_IS_EMPTY")
    @NotNull(message = "ROLE_IS_EMPTY")
    private List<Integer> roles;
}
