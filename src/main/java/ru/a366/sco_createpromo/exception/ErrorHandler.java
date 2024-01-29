package ru.a366.sco_createpromo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @AllArgsConstructor
    @Getter
    public static class ApiError {
        private final String code;
        private final String desc;
    }

    private final String header = "Internal error, please contact administrator. ";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationExceptions(
            MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder("Не заполнены обязательные поля: ");
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            sb.append(fieldName).append(", ");
        });
        sb.setLength(sb.length() - 2);
        log.error(sb.toString());
        return new ApiError("error", header + sb.toString());
    }

    @ExceptionHandler(RubblesDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleRubblesDataException(
            RubblesDataException e) {
        log.error(e.getMessage());
        return new ApiError("error", header + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleException(
            Exception e) {
        log.error(e.getMessage(), e);
        return new ApiError("error", header + e.getMessage());
    }

}
