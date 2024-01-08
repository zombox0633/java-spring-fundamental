package com.springframework.springfundamental.service;

import com.springframework.springfundamental.constants.ErrorMessage;
import com.springframework.springfundamental.dto.keyboard.PostKeyboardRequest;
import com.springframework.springfundamental.dto.keyboard.KeyboardRequest;
import com.springframework.springfundamental.dto.keyboard.PutKeyboardRequest;
import com.springframework.springfundamental.entity.Keyboard;
import com.springframework.springfundamental.exception.InvalidException;
import com.springframework.springfundamental.exception.NotFoundException;
import com.springframework.springfundamental.repository.KeyboardRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.springframework.springfundamental.constants.ErrorMessage.INVALID_SORT_BY;

@Service
@RequiredArgsConstructor
public class KeyboardService {

    private final KeyboardRepository keyboardRepository;

    //GET
    //    public String getAllKeyboard() {
    //        return "GET ALL Keyboard";
    //    }

    //GET ALL
    public List<Keyboard> getAllKeyboard() {
        return keyboardRepository.findAll();
    }

    //GET Pagination
    /*public List<Keyboard> getAllKeyboardWithPagination(int page, int size, String sortBy, String order){
        Pageable pageable;

        //Setup default page and size value
        page = page <= 0 ? 1 : page -1; //เพราะการเก็บข้อมูล Spring Data JPA จะเริ่มต้นจาก 0 ไม่ใช่ 1 จึงต้องลดค่า 1
        size = size <= 0 ? 10 : size;

        if(StringUtils.isAllBlank(sortBy)){
            // Setup order direction
            var orderBy = StringUtils.isBlank(order) ? Sort.Direction.ASC : Sort.Direction.valueOf(order.toUpperCase());

            //Validate sort by specific column
            if (!isSortByValid(sortBy)){ //ตรวจสอบค่า sortBy
                throw new InvalidException(INVALID_SORT_BY.formatted(sortBy));
            }

            pageable = PageRequest.of(page, size, orderBy, sortBy);
        }else {
            pageable = PageRequest.of(page, size);
        }

        var keyboardPagination = keyboardRepository.findAll(pageable);
        return keyboardPagination.getContent();
    }*/

    public List<Keyboard> getAllKeyboardWithPagination(int page, int size, String[] sortBy, String order){
        Pageable pageable;

        page = page <= 0 ? 1 : page -1;
        size = size <= 0 ? 10 : size;

        var orderBy = StringUtils.isBlank(order) ? Sort.Direction.ASC : Sort.Direction.valueOf(order.toUpperCase());

        if(sortBy == null || sortBy.length == 0){
            sortBy = new String[] {"name"};
        }

        var sorting = Sort.by(Arrays.stream(sortBy) //แปลง Arrays เป็นสตรีม
                .map(data -> new Sort.Order(orderBy,data))
                .toList());

        pageable = PageRequest.of(page,size,sorting);
        var keyboardPagination = keyboardRepository.findAll(pageable);

        return keyboardPagination.getContent();
    }

    //GET BY ID
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
    public Keyboard saveKeyboardVB(PostKeyboardRequest request){

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
    public Keyboard updateKeyboard(String id, PutKeyboardRequest request){
        var existingKeyboard = getKeyboardById(id);

        if(request.categoryId() != null){
            existingKeyboard.setCategoryId(UUID.fromString(request.categoryId()));
        }
        if(request.name() != null){
            existingKeyboard.setName(request.name());
        }
        if(request.price() != null){
            existingKeyboard.setPrice(BigDecimal.valueOf(request.price()));
        }

        existingKeyboard.setQuantity(request.quantity());
        existingKeyboard.setLastOpId(UUID.fromString(request.lastOpId()));

        keyboardRepository.save(existingKeyboard);
        return existingKeyboard;
    }

    //DELETE
    public void deleteKeyboard(String id){
        var exisingKeyboard = getKeyboardById(id);
        keyboardRepository.delete(exisingKeyboard);
    }

    private boolean isSortByValid(String sortBy){
        return switch (sortBy){
            case "name","price" -> true;
            default -> false;
        };
    }

}
