package ru.psu.job.search.web.response;

import org.springframework.http.HttpStatus;

public class SuccessResponse<T> extends Response {
    private SuccessResponse(HttpStatus httpStatus, Object result) {
        super(httpStatus, result);
    }

    public static <T> SuccessResponse<T> of(T result) {
        return new SuccessResponse<>(HttpStatus.OK, result);
    }

    public static <T> SuccessResponse<T> ofEmpty() {
        return new SuccessResponse<>(HttpStatus.OK, null);
    }
}
