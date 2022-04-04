package edu.shmonin.userservice.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionData {

    private String message;
    private LocalDateTime dateTime;
}
