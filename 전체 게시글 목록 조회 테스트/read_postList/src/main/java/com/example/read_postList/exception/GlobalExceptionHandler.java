package com.example.read_postList.exception;

import com.example.read_postList.dto.response.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class, IllegalStateException.class})
    public ResponseEntity<ResponseDto<?>> handleBadRequest(Exception e){
        e.printStackTrace();
        ResponseDto<?> response = ResponseDto.setFailed("Bad Request: " + e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDto<?>> handleAccessDenied(AccessDeniedException e){
        e.printStackTrace();
        ResponseDto<?> response = ResponseDto.setFailed("Access Denied " + e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> handleNotFoundException(EntityNotFoundException e){
        e.printStackTrace();
        ResponseDto<?> response = ResponseDto.setFailed("Not Found " + e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto<?>> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        e.printStackTrace();
        ResponseDto<?> response = ResponseDto.setFailed("Conflict " + e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<?>> handleException(Exception e){
        e.printStackTrace();
        ResponseDto<?> response = ResponseDto.setFailed("Internal Server Error " + e.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

}
