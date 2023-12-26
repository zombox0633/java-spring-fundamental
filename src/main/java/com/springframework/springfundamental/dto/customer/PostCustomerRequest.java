package com.springframework.springfundamental.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PostCustomerRequest(
    @NotNull
    @Email
    String email,

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])(?=\\S+$).{8,24}$")
    String password,

    @NotNull
    String name,

    @NotNull
    String birthday
) {
}
