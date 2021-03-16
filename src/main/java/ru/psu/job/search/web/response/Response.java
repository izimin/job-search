package ru.psu.job.search.web.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public abstract class Response {
    @JsonIgnore
    private final HttpStatus httpStatus;
    private final Object result;

    protected Response(HttpStatus httpStatus, Object result) {
        this.httpStatus = httpStatus;
        this.result = result;
    }

    @SuppressWarnings("unchecked")
    public <T> ResponseEntity<T> build() {
        return (ResponseEntity<T>) ResponseEntity.status(this.httpStatus).body(result);
    }
}
