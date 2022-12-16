package com.testycool.testycoolserver.shared.classes;

import com.testycool.testycoolserver.shared.dtos.ErrorResponse;
import com.testycool.testycoolserver.shared.exceptions.DataExistException;
import com.testycool.testycoolserver.shared.exceptions.NotFoundException;
import com.testycool.testycoolserver.shared.exceptions.UnsuitableQuestionException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "Data Not Found",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DataExistException.class)
    public ResponseEntity<ErrorResponse> handleDataExistException(DataExistException e) {
        ErrorResponse response = new ErrorResponse<>(
                HttpStatus.CONFLICT.value(),
                "Data Already Exist",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(UnsuitableQuestionException.class)
    public ResponseEntity<ErrorResponse> handleUnsuitableQuestionException(UnsuitableQuestionException e) {
        ErrorResponse response = new ErrorResponse<>(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Unsuitable Data",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<List<String>>> handleMethodArgumentException(MethodArgumentNotValidException e) {
        List<String> fieldErrors = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorResponse<List<String>> response = new ErrorResponse<>(
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), fieldErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
