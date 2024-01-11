package com.springframework.springfundamental.dto.keyboard;

import jakarta.validation.constraints.NotNull;

public record KeyboardSearchCriteria(
        @NotNull
        int page,

        @NotNull
        int size,

        String sort,

        String order,

        KeyboardSearch keyboardSearch
) {
}
