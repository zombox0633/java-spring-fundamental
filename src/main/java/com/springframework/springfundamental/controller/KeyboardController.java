package com.springframework.springfundamental.controller;

import com.springframework.springfundamental.entity.Keyboard;
import com.springframework.springfundamental.service.KeyboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping(value = "/:id")
    public String getKeyboardById(){
        return "GET KeyboardById ";
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED) //201 Created และ ResponseStatus คือการระบุบ HttpStatus ในกรณีที่ส่งสำเร็จ
    public String postKeyboard(){
        return "POST Keyboard";
    }

    @PutMapping(value = "")
    public String putKeyboard(){
        return "PUT Keyboard";
    }

    @DeleteMapping(value = "")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204 No Content
    public void deleteKeyboard(){
        // status ถ้าใช้งาน HttpStatus.NO_CONTENT แม้จะใช้ข้อความไปใน method ก็จะออกมาเฉพาะ HttpStatus
    }

//    @PutMapping(value = "/2")
//    public ResponseEntity<?> updateKeyboard2(){
//        return ResponseEntity.badRequest().body("Error");
//    }
}
