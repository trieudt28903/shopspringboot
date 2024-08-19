package com.dev.shopdienthoai.demo.until.error;

import com.dev.shopdienthoai.demo.domain.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GloballException {
    @ExceptionHandler(value = {UsernameNotFoundException.class, BadCredentialsException.class, IdInvalidException.class})
    public ResponseEntity<RestResponse<Object>> handleIdInvalidException(Exception e) {
        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse.setMessage("Exception occurs...");
        restResponse.setErrorMessage(e.getMessage());
        restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        restResponse.setErrorMessage("MethodArgumentNotValidException");
        List<String> errorMessages = fieldErrors.stream()
                .map(f->f.getDefaultMessage())
                .collect(Collectors.toList());
        restResponse.setMessage(errorMessages.size() > 1 ? errorMessages : errorMessages.get(0));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
    }
    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<RestResponse<Object>> handleNoResourceFoundException(NoResourceFoundException e) {
        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        restResponse.setErrorMessage("Resource not found");
        restResponse.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restResponse);
    }
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<RestResponse<Object>> handleNullPointerException(NullPointerException e) {
        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        restResponse.setErrorMessage("NullPointerException");
        restResponse.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
    }
}
