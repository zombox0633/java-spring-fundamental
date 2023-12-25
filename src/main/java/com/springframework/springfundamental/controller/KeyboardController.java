package com.springframework.springfundamental.controller;

import com.springframework.springfundamental.dto.keyboard.PostKeyboardRecord;
import com.springframework.springfundamental.dto.keyboard.PutKeyboardRecord;
import com.springframework.springfundamental.entity.Keyboard;
import com.springframework.springfundamental.service.KeyboardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@... คือ annotation ที่ใช้กำหนดคลาสให้เป็น Spring MVC Controller
@RestController // การกำหนดคลาสเพื่อใช้งานเป็น Controller ในรูปแบบของ REST API และ  จะส่งข้อมูลกลับเป็นรูปแบบของ JSON หรือ XML
@RequestMapping(value = "v1/keyboard") // ใช้กำหนด path หรือ URL สำหรับ mapping การร้องขอ (requests) ใน RESTful web service
@RequiredArgsConstructor // import ในรูปแบบใหม่
public class KeyboardController {

    /*@Autowired  //import แบบเก่า ในรูปแบบ java
    private KeyboardService keyboardService;*/

    private final KeyboardService keyboardService;

//    @RequestMapping(value = "/keyboard", method = RequestMethod.GET)
//    public String getKeyboardOld(){
//        return "Keyboard";
//    }

    @GetMapping(value = "")
    public List<Keyboard> getKeyboard(){
        return keyboardService.getAllKeyboard();
    }

    //getKeyboardById
    @GetMapping(value = "/path/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Keyboard getKeyboardByIdWithPathVariable(@PathVariable("id") String id){
        return keyboardService.getKeyboardById(id);
    }

    @GetMapping(value = "/req")
    @ResponseStatus(HttpStatus.OK)
    public Keyboard getKeyboardByIdWithRequestParam(@RequestParam("id") String id){
        return keyboardService.getKeyboardById(id);
    }

    //Post
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED) //201 Created และ ResponseStatus คือการระบุบ HttpStatus ในกรณีที่ส่งสำเร็จ
    public Keyboard postKeyboard(@Valid @RequestBody PostKeyboardRecord keyboardRequest){
        //@Valid ใส่เมื่อต้องการใช้งาน validation ตามที่กำหนดใน KeyboardRecord
        //Statement
        return keyboardService.saveKeyboardVB(keyboardRequest);
    }

    //PUT
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Keyboard putKeyboard(@Valid @PathVariable("id") String id, @RequestBody PutKeyboardRecord request){
        return keyboardService.updateKeyboard(id, request);
    }

    //DELETE
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204 No Content
    public void deleteKeyboard(@PathVariable("id") String id){
        // status ถ้าใช้งาน HttpStatus.NO_CONTENT แม้จะใช้ข้อความไปใน method ก็จะออกมาเฉพาะ HttpStatus
        keyboardService.deleteKeyboard(id);
    }

//    @PutMapping(value = "/2")
//    public ResponseEntity<?> updateKeyboard2(){
//        return ResponseEntity.badRequest().body("Error");
//    }
}
