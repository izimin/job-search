package ru.psu.job.search.web.response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
@ResponseBody
@Slf4j
@RequiredArgsConstructor
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            EntityNotFoundException.class,
            IllegalArgumentException.class,
            IllegalArgumentException.class
    })
    protected ResponseEntity<Object> handleBaseException(Exception exception) {
        return ErrorResponse.of(exception.getMessage()).build();
    }

}
