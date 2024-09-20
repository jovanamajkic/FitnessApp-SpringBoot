package com.example.fitnessapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends HttpException {
    public UnauthorizedException(){
        super(HttpStatus.UNAUTHORIZED, null);
    }

    public UnauthorizedException(Object data){
        super(HttpStatus.UNAUTHORIZED, data);
    }
}
