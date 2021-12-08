package com.cornello.prototype.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.SecureRandom;

@ControllerAdvice
public class ExceptionHelper extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ArithmeticException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String errorId = generateRandomPassword(9);
        String bodyOfResponse = String.format("%s %s errorId:%s",ex,request,errorId);
        logger.error("stack0: "+ex.getStackTrace()[0]
                +"\nstack1: "+ex.getStackTrace()[1]
                +"\nstack2: "+ex.getStackTrace()[2]
                +"\nerrorId:"+errorId);
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    public String generateRandomPassword(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                +"lmnopqrstuvwxyz!@#$%&";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(secureRandom.nextInt(chars.length())) );
        return sb.toString();
    }
}