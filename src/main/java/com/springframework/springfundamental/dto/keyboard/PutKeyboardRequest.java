package com.springframework.springfundamental.dto.keyboard;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public record PutKeyboardRequest(
        @UUID
        String categoryId,


        String name,

        @Min(0) //validation
        int quantity,

        @Digits(integer = 9, fraction = 4) //validation //กำหนดตาม postgres
        @Min(0)
        Double price,

        @NotNull //validation
        @UUID
        String lastOpId
){
}
