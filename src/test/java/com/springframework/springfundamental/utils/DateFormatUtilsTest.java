package com.springframework.springfundamental.utils;

import com.springframework.springfundamental.exception.InvalidException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class DateFormatUtilsTest {

    //Single Test Case
    @Test
    void testForStringToLocalDate() {
        var expect = LocalDate.of(2021, 1, 1);

        var inputDate = "2021-01-01";
        var actual = DateFormatUtils.stringToLocalDate(inputDate);

        assertThat(actual).isEqualTo(expect);
    }

    //ทดสอบหลายตัว1
    @ParameterizedTest
    @CsvSource(value = {
            "2021-01-01, 2021-01-01",
            "2022-02-01, 2022-02-01",
            "2023-03-03, 2023-03-03"
    }, delimiter = ',')
    void testForStringToLocalDateWithParameterizedTestByCsvSource(String inputDate, String expect){
        var df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var expectDate = LocalDate.parse(expect, df);

        var actualDate = DateFormatUtils.stringToLocalDate(inputDate);
        assertThat(actualDate).isEqualTo(expectDate);
    }

    //ทดสอบหลายตัว2
    @ParameterizedTest
    @MethodSource("sourceForStringToLocalDateWithParameterizedTestByMethodSource")
    void testForStringToLocalDateWithParameterizedTestByMethodSource(String inputDate, LocalDate expect){
        var actualDate = DateFormatUtils.stringToLocalDate(inputDate);
        assertThat(actualDate).isEqualTo(expect);
    }
    private static Stream<Arguments> sourceForStringToLocalDateWithParameterizedTestByMethodSource(){
        var df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return Stream.of(
                Arguments.of("2021-01-01", LocalDate.parse("2021-01-01", df)),
                Arguments.of("2022-02-01", LocalDate.parse("2022-02-01", df)),
                Arguments.of("2023-03-03", LocalDate.parse("2023-03-03", df))
        );
    }


    //ทดสอบการเกิด Exception
    @Test
    void testForStringToLocalDateWithException() {
        assertThatThrownBy(() -> DateFormatUtils.stringToLocalDate("2021-01-01-01"))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Invalid date format. Please use yyyy-MM-dd");
    }
}
