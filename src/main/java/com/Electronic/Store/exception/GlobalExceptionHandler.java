package com.Electronic.Store.exception;

import com.Electronic.Store.dto.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //handler resource not found exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resouceNotfoundExceptionHandler(ResourceNotFoundException ex){
           logger.info("Exception handler invoked!!!");
        ApiResponseMessage response=ApiResponseMessage.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .success(true).build();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    //methodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidExceptin(MethodArgumentNotValidException ex){
        List<ObjectError> allErrors=ex.getBindingResult().getAllErrors();

        Map<String,Object> response=new HashMap<>();

        allErrors.stream().forEach(objectError->{
            String message=objectError.getDefaultMessage();
            String filed=((FieldError)objectError).getField();
            response.put(filed,message);
        });
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    //handle bad api exception
    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponseMessage> handlerBadApiRequest(BadApiRequestException ex){
        logger.info("Bad api request");
        ApiResponseMessage response=ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
