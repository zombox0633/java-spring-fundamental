package com.springframework.springfundamental.service;

import com.springframework.springfundamental.constants.ErrorMessage;
import com.springframework.springfundamental.dto.keyboard.PostKeyboardRecord;
import com.springframework.springfundamental.dto.keyboard.KeyboardRequest;
import com.springframework.springfundamental.dto.keyboard.PutKeyboardRecord;
import com.springframework.springfundamental.entity.Keyboard;
import com.springframework.springfundamental.exception.NotFoundException;
import com.springframework.springfundamental.repository.KeyboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeyboardService {

    private final KeyboardRepository keyboardRepository;

    //GET
    //    public String getAllKeyboard() {
    //        return "GET ALL Keyboard";
    //    }

    public List<Keyboard> getAllKeyboard() {
        return keyboardRepository.findAll();
    }

    public Keyboard getKeyboardById(String id){
        return keyboardRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("Keyboard")));
    }

    //POST
    //แบบปกติ
    @Deprecated
    public Keyboard saveKeyboard(KeyboardRequest request){

        var keyboard = new  Keyboard();
        keyboard.setId(UUID.randomUUID());
//        keyboard.setCategoryId(UUID.fromString(request.getCategoryId()));
        keyboard.setName(request.getName());
        keyboard.setQuantity(request.getQuantity());
        keyboard.setPrice(BigDecimal.valueOf(request.getPrice()));
//        keyboard.setCreateTimestamp(ZonedDateTime.now());
//        keyboard.setLastUpdatedTimestamp(ZonedDateTime.now());
        keyboard.setLastOpId(UUID.fromString(request.getLastOpId()));

        keyboardRepository.save(keyboard);
        return keyboard;
    }

    // แบบใช้ Record
    public Keyboard saveKeyboardVB(PostKeyboardRecord request){

        var keyboard = new Keyboard();
        keyboard.setCategoryId(UUID.fromString(request.categoryId()));
        keyboard.setName(request.name());
        keyboard.setQuantity(request.quantity());
        keyboard.setPrice(BigDecimal.valueOf(request.price()));
        keyboard.setLastOpId(UUID.fromString(request.lastOpId()));

        keyboardRepository.save(keyboard);
        return keyboard;
    }

    //PUT
    public Keyboard updateKeyboard(String id, PutKeyboardRecord request){

        var existingKeyboard = getKeyboardById(id);
        existingKeyboard.setCategoryId(UUID.fromString(request.categoryId()));
        existingKeyboard.setName(request.name());
        existingKeyboard.setQuantity(request.quantity());
        existingKeyboard.setPrice(BigDecimal.valueOf(request.price()));
        existingKeyboard.setLastOpId(UUID.fromString(request.lastOpId()));

        keyboardRepository.save(existingKeyboard);
        return existingKeyboard;
    }

    //DELETE
    public void deleteKeyboard(String id){
        var exisingKeyboard = getKeyboardById(id);
        keyboardRepository.delete(exisingKeyboard);
    }

}
