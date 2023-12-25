package com.springframework.springfundamental.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//กำหนดว่า constructor ที่ถูกสร้างขึ้นนี้จะมีระดับการเข้าถึงเป็น private, ซึ่งหมายความว่าไม่สามารถสร้าง instance ของ class นี้จากนอก class ได้
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateFormatUtils {

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    public static LocalDate stringToLocalDate(String date) {
        var formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        return LocalDate.parse(date, formatter);
    }
}
