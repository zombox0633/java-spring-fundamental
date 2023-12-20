package com.springframework.springfundamental.exception;

// RuntimeException คือ error ที่ใหญ่ที่สุดของ java
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
