package com.springframework.springfundamental.utils;

import com.springframework.springfundamental.exception.InvalidException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//กำหนดว่า constructor ที่ถูกสร้างขึ้นนี้จะมีระดับการเข้าถึงเป็น private, ซึ่งหมายความว่าไม่สามารถสร้าง instance ของ class นี้จากนอก class ได้
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateFormatUtils {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static LocalDate stringToLocalDate(String date) {
        try {
            var formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            return LocalDate.parse(date, formatter);
        }catch (Exception e){
            throw new InvalidException("Invalid date format. Please use yyyy-MM-dd");
        }
    }
}
