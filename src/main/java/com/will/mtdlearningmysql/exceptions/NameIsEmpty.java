package com.will.mtdlearningmysql.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.BAD_REQUEST)
public class NameIsEmpty extends IllegalStateException{

    public NameIsEmpty(String message) {
        super(message);
    }
}
