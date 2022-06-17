package com.cornello.prototype.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

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

    @Override
    protected ResponseEntity<Object>  handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), headers, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex.getAllErrors(), headers, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        String exceptionName = mostSpecificCause.getClass().getName();
        String message = mostSpecificCause.getMessage();
        return new ResponseEntity<>(new ErrorMessage(exceptionName, message, ex.getMessage()), headers, status);
    }

@Data
@AllArgsConstructor
class ErrorMessage {
	private String message;
	private String exceptionName;
    private String exceptionMessage;
}

}