package com.springframework.springfundamental.service;

import com.springframework.springfundamental.entity.Keyboard;
import com.springframework.springfundamental.repository.KeyboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyboardService {

    private final KeyboardRepository keyboardRepository;

//    public String getAllKeyboard() {
//        return "GET ALL Keyboard";
//    }

    public List<Keyboard> getAllKeyboard() {
        return keyboardRepository.findAll();
    }

}
