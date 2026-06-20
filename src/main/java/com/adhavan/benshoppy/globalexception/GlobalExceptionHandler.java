package com.adhavan.benshoppy.globalexception;


import com.adhavan.benshoppy.dto.ErrorResponse;
import com.adhavan.benshoppy.globalexception.customexception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

     @ExceptionHandler(ResourceAlreadyExistsException.class)
     public ResponseEntity<ErrorResponse> resourceAlreadyExist(ResourceAlreadyExistsException ex){

         return ResponseEntity.status(409)
                 .body(new ErrorResponse(ex.getMessage(), LocalDateTime.now()));

     }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(ResourceNotFoundException ex){

        return ResponseEntity.status(404)
                .body(new ErrorResponse(ex.getMessage() , LocalDateTime.now()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> validationHandle(MethodArgumentNotValidException ex){

         Map<String,String> errors = new HashMap<>();

         ex.getBindingResult().getFieldErrors().
                 forEach(error ->
                         errors.put(error.getField(),error.getDefaultMessage()) );
         return ResponseEntity.status(400).body(errors);

    }

    @ExceptionHandler(RequestImageNotFound.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(RequestImageNotFound ex){

        return ResponseEntity.status(404)
                .body(new ErrorResponse(ex.getMessage() , LocalDateTime.now()));

    }

    @ExceptionHandler(ProductImageNotFound.class)
    public ResponseEntity<ErrorResponse> productImageNotFound(ProductImageNotFound ex){

        return ResponseEntity.status(404)
                .body(new ErrorResponse(ex.getMessage() , LocalDateTime.now()));

    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> insufficientStock(InsufficientStockException ex){

        return ResponseEntity.status(500)
                .body(new ErrorResponse(ex.getMessage() , LocalDateTime.now()));

    }
    @ExceptionHandler(MaxLimitForImage.class)
    public ResponseEntity<ErrorResponse> maxLimitForImage(MaxLimitForImage ex){

        return ResponseEntity.status(400)
                .body(new ErrorResponse(ex.getMessage() , LocalDateTime.now()));

    }
    @ExceptionHandler(OrderAlreadyCancelledException.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(OrderAlreadyCancelledException ex){

        return ResponseEntity.status(409)
                .body(new ErrorResponse(ex.getMessage() , LocalDateTime.now()));

    }

    @ExceptionHandler(FileFormatWrongException.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(FileFormatWrongException ex){

        return ResponseEntity.status(409)
                .body(new ErrorResponse(ex.getMessage() , LocalDateTime.now()));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleException(Exception ex){

         ex.printStackTrace();

         Map<String,String> res = new HashMap();
         res.put("error ", "something went wrong ");
         res.put("message", ex.getMessage());

        return ResponseEntity.status(500)
                .body(res);

    }



}
