package com.springframework.springfundamental.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public record CategoryRequest(
        @NotNull
        String name,

        @NotNull
        @UUID
        String lastOpId
) {
}
