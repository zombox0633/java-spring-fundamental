package com.springframework.springfundamental.dto.keyboard;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PutKeyboardRecord (

        String categoryId,


        String name,

        @Min(0) //validation
        int quantity,

        @Digits(integer = 9, fraction = 4) //validation //กำหนดตาม postgres
        @Min(0)
        Double price,

        @NotNull //validation
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
        String lastOpId
){
}
