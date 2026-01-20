package com.sm.jeyz9.storemateapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WebException extends RuntimeException{
    private final HttpStatus status;
    
    public WebException(HttpStatus status, String message){
        super(message);
        this.status = status;
    }
}
