package unittesting;

import com.project.bll.util.DateTimeConverter;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeConverterTest {

    @Test
    void formatDateTimeTest(){

        String expectedValue = "2002-05-03 22:22";// pattern yyyy-MM-dd HH:mm
        String expectedFalseDate = null;

        LocalDateTime dateToFormat = LocalDateTime.of(2002,5,3,22,22);
        LocalDateTime falseDate = null;

        String actualFalseDate = DateTimeConverter.formatDateTime(falseDate);
        String actualValue = DateTimeConverter.formatDateTime(dateToFormat);

        assertAll("Test for formatting LocalDateTime to String",
                () -> assertEquals(expectedValue,actualValue),
                () -> assertEquals(expectedFalseDate,actualFalseDate));

    }
}