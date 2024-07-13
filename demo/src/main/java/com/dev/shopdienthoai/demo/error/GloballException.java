package com.dev.shopdienthoai.demo.error;

import com.dev.shopdienthoai.demo.domain.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GloballException {
    @ExceptionHandler(value = IdInvalidException.class)
    public ResponseEntity<RestResponse<Object>> handleIdInvalidException(IdInvalidException e) {
        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse.setMessage("CALL API FAILED");
        restResponse.setErrorMessage(e.getMessage());
        restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
    }
}
