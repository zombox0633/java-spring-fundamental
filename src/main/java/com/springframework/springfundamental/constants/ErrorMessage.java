package com.springframework.springfundamental.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE) //ไม่สามารถ new หรือ แก้ไขอะไรกำ method ได้สามารถใช้ได้อย่างเดียว
public class ErrorMessage {
    //static จะสร้างเป็นลำดับแรกๆ และ จะเป็นค่าเดิมในทุกที่ที่ใช้มัน *ชื่อตัวที่ใช้งาน static ในการสร้างส่วนใหญ่จะเป็น uppercase ทั้งหมด
    //final ไม่สามารถเปลี่ยนแปลงค่าได้หลังจากที่ถูก assign ค่าเริ่มต้น
    public static final String NOT_FOUND = "%s not found";
}
