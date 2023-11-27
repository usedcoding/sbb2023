package com.example.sbb1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotExecption extends RuntimeException{
    public DataNotExecption(String msg) {
        super(msg);
    }

}
