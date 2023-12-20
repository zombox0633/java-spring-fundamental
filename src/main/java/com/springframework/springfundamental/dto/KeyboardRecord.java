package com.springframework.springfundamental.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record KeyboardRecord(
        @NotNull //validation
        String categoryId,

        @NotNull //validation
        String name,

        @Min(0) ////validation
        int quantity,

        @Digits(integer = 9, fraction = 4) //validation //กำหนดตาม postgres
        @Min(0)
        Double price,

        @NotNull
        String lastOpId
) {
}
