package com.springframework.springfundamental.dto;

public record GenericResponse(
        String path,
        String error,
        Object reason
) {
}
