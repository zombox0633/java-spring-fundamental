package com.springframework.springfundamental.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeyboardRequest {

    private String categoryId;

    private String name;

    private int quantity;

    private Double price;

    private String lastOpId;
}
