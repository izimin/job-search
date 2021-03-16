package ru.psu.job.search.web.response;

import org.springframework.http.HttpStatus;

public class ErrorResponse<T> extends Response {
    private ErrorResponse(HttpStatus httpStatus, Object result) {
        super(httpStatus, result);
    }

    public static <T> ErrorResponse<T> of(T result) {
        return new ErrorResponse<>(HttpStatus.BAD_REQUEST, result);
    }

    public static <T> ErrorResponse<T> ofEmpty() {
        return new ErrorResponse<>(HttpStatus.BAD_REQUEST, null);
    }

    public static <T> ErrorResponse<T> ofNotFound() {
        return new ErrorResponse<>(HttpStatus.NOT_FOUND, null);
    }
}
