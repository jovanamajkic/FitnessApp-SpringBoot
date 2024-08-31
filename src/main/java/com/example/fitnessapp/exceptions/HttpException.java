package com.example.fitnessapp.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class HttpException extends RuntimeException {
    private HttpStatus status;
    private Object data;

    public HttpException() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        data = null;
    }

    public HttpException(Object data){
        this(HttpStatus.INTERNAL_SERVER_ERROR, data);
    }

    public HttpException(HttpStatus status, Object data){
        this.status = status;
        this.data = data;
    }
}
