package com.springframework.springfundamental.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PostCustomerRecord(
    @NotNull
    @Email
    String email,

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,24}$")
    String password,

    @NotNull
    String name,

    @NotNull
    String birthday
) {
}
