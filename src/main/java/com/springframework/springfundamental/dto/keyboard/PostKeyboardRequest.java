package com.springframework.springfundamental.dto.keyboard;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public record PostKeyboardRequest(
        @NotNull
        @UUID
        String categoryId,

        @NotNull
        String name,

        @NotNull
        @Min(0) //validation
        int quantity,

        @NotNull
        @Digits(integer = 9, fraction = 4) //validation //กำหนดตาม postgres
        @Min(0)
        Double price,

        @NotNull //validation
        @UUID
        String lastOpId
) {
}
