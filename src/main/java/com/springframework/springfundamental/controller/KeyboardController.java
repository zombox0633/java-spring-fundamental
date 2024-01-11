package com.springframework.springfundamental.controller;

import com.springframework.springfundamental.dto.keyboard.KeyboardSearch;
import com.springframework.springfundamental.dto.keyboard.PostKeyboardRequest;
import com.springframework.springfundamental.dto.keyboard.PutKeyboardRequest;
import com.springframework.springfundamental.entity.Keyboard;
import com.springframework.springfundamental.service.KeyboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Keyboard", description = "APIs for managing keyboard data.")
public class KeyboardController {

    /*@Autowired  //import แบบเก่า ในรูปแบบ java
    private KeyboardService keyboardService;*/

    private final KeyboardService keyboardService;

//    @RequestMapping(value = "/keyboard", method = RequestMethod.GET)
//    public String getKeyboardOld(){
//        return "Keyboard";
//    }

    //GET
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve All Keyboard Data",
            description = "Returns a list of all keyboards available in the system.")
    public List<Keyboard> getKeyboard(){
        return keyboardService.getAllKeyboard();
    }

    //GET Pagination
//    @GetMapping(value = "/pagination")
//    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Get all keyboard with pagination",description = "Get all keyboard in table with pagination")
//    public List<Keyboard> getAllKeyboardWithPagination(
//            @RequestParam("page") int page,
//            @RequestParam("size") int size,
//            @RequestParam(value = "sort", defaultValue = "name") String sort,
//            @RequestParam(value = "order", defaultValue = "asc") String order
//    ){
//        //{{localhost}}/v1/keyboard/pagination?page=1&size=8&sort=name&order=asc
//        return keyboardService.getAllKeyboardWithPagination(page, size, sort, order);
//    }

    @GetMapping(value = "/pagination")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all keyboard with pagination",description = "Get all keyboard in table with pagination")
    public List<Keyboard> getAllKeyboardWithPagination(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "sort", defaultValue = "name") String[] sort,
            @RequestParam(value = "order", defaultValue = "asc") String order
    ){
        //"Method Overloading
        //{{localhost}}/v1/keyboard/pagination?page=1&size=8&sort=name,price,quantity&order=asc
        return keyboardService.getAllKeyboardWithPagination(page, size, sort, order);
    }

    //dynamic search
    @PostMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all keyboard with filter", description = "Get all keyboard in table with filter")
    public List<Keyboard> getAllKeyboardWithFilter(@RequestBody KeyboardSearch keyboardSearch){
        return keyboardService.getAllKeyboardWithSearch(keyboardSearch);
    }

    //GET KeyboardById
    @GetMapping(value = "/path/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve Keyboard Data by Id",
            description = "Returns keyboard data for the specified Id.")
    public Keyboard getKeyboardByIdWithPathVariable(@PathVariable("id") String id){
        //http://localhost:8080/v1/keyboard/path/d1e981f6-d886-4a4f-9676-3e11c8b38a98
        //ใช้เมื่อต้องการดึงค่าที่ไม่จำเป็นต้องเป็นส่วนหนึ่งของ path ของ URL, เหมาะสำหรับการส่งข้อมูลที่ไม่เป็นส่วนความลับหรือไม่มีความสำคัญเชิงโครง

        return keyboardService.getKeyboardById(id);
    }

    @GetMapping(value = "/req")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve Keyboard Data by ID (Using Request Param)",
            description = "Returns keyboard data for the specified ID using request parameters.")
    public Keyboard getKeyboardByIdWithRequestParam(@RequestParam("id") String id){
        //http://localhost:8080/v1/keyboard/req?id=d1e981f6-d886-4a4f-9676-3e11c8b38a98
        //ใช้เมื่อต้องการดึงค่าที่เป็นส่วนหนึ่งของ URI path และมีความสำคัญต่อโครงสร้าง

        return keyboardService.getKeyboardById(id);
    }

    //Post
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED) //201 Created และ ResponseStatus คือการระบุบ HttpStatus ในกรณีที่ส่งสำเร็จ
    @Operation(summary = "Add New Keyboard Data", description = "Creates and saves new keyboard data in the system.")
    public Keyboard postKeyboard(@Valid @RequestBody PostKeyboardRequest keyboardRequest){
        //@Valid ใส่เมื่อต้องการใช้งาน validation ตามที่กำหนดใน KeyboardRecord
        //Statement
        return keyboardService.saveKeyboardVB(keyboardRequest);
    }

    //PUT
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update Keyboard Data", description = "Updates keyboard data for the specified ID.")
    public Keyboard putKeyboard(@Valid @PathVariable("id") String id, @RequestBody PutKeyboardRequest request){
        return keyboardService.updateKeyboard(id, request);
    }

    //DELETE
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204 No Content
    @Operation(summary = "Delete Keyboard Data",
            description = "Deletes keyboard data from the system based on the specified ID.")
    public void deleteKeyboard(@PathVariable("id") String id){
        // status ถ้าใช้งาน HttpStatus.NO_CONTENT แม้จะใช้ข้อความไปใน method ก็จะออกมาเฉพาะ HttpStatus
        keyboardService.deleteKeyboard(id);
    }

//    @PutMapping(value = "/2")
//    public ResponseEntity<?> updateKeyboard2(){
//        return ResponseEntity.badRequest().body("Error");
//    }
}
