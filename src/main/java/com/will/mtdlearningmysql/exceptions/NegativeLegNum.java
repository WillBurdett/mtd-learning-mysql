package com.will.mtdlearningmysql.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.BAD_REQUEST)
public class NegativeLegNum extends IllegalStateException{

    public NegativeLegNum(String message) {
        super(message);
    }
}
