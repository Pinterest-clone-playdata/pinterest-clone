package com.team1.pinterest.Exception;

import com.team1.pinterest.DTO.Basic.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.team1.pinterest.Exception.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ResponseDTO<?>> handleDataException() {
        log.error("handleDataException throw Exception : {}", DUPLICATE_RESOURCE);
        return ResponseDTO.toResponseEntity(DUPLICATE_RESOURCE);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<ResponseDTO<?>> handleMaxSizeException() {
        log.error("handleDataException throw Exception : {}", OVER_FILESIZE);
        return ResponseDTO.toResponseEntity(OVER_FILESIZE);
    }

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ResponseDTO<?>> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ResponseDTO.toResponseEntity(e.getErrorCode());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder stringBuilder = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(", ");
        }

        ResponseDTO<Object> response = ResponseDTO.builder().message(stringBuilder.toString()).status(400).build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<Object> handleIllegalException(IllegalStateException illegalStateException){
        String message = illegalStateException.getMessage();
        ResponseDTO<Object> response = ResponseDTO.builder().message(message).status(400).build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException){
        String message = illegalArgumentException.getMessage();
        ResponseDTO<Object> response = ResponseDTO.builder().message(message).status(400).build();

        return ResponseEntity.badRequest().body(response);
    }
}
