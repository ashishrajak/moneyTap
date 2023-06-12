package com.moneyTap.moneyTap.controllers;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AdviceControllerf {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> argumentNotFout(MethodArgumentNotValidException ex){
        Map<String ,String> map= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(
                (error)->{
                    String keyError= ((FieldError)error).getField();
                    String errors=error.getDefaultMessage();
                    map.put(keyError,errors);
                }

        );
        return map;
    }
}
