package com.fastcampus.programming.dmaker.exception;

import com.fastcampus.programming.dmaker.dto.DMakerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.fastcampus.programming.dmaker.exception.DMakerErrorCode.INTERNAL_SERVER_ERROR;
import static com.fastcampus.programming.dmaker.exception.DMakerErrorCode.INVALID_REQUEST;

// bean 으로 등록

@Slf4j
@RestControllerAdvice
public class DMakerExceptionHandler {
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DMakerException.class)
    public DMakerErrorResponse handlerException(
            DMakerException e,
            HttpServletRequest request) {
        log.error("errorCode: {}, url: {}, message: {}", e.getDMakerErrorCode(), request.getRequestURI(), e.getDetailMessage());
        return DMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }

    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class, // curd mapping 없을때
            MethodArgumentNotValidException.class // validation 문제가 발생할 때  컬트롤러 내부 들어오기 전
    })
    public DMakerErrorResponse handleBadRequest(
            Exception e,
            HttpServletRequest request
    ) {
        log.error("url: {}, message: {}", request.getRequestURI(), e.getMessage());
        return DMakerErrorResponse.builder()
                .errorCode(INVALID_REQUEST)
                .errorMessage(INVALID_REQUEST.getMessage())
                .build();
    }


    @ExceptionHandler(Exception.class) // 최후의 보루
    public DMakerErrorResponse handleException(
            Exception e,
            HttpServletRequest request
    ) {
        log.error("url: {}, message: {}", request.getRequestURI(), e.getMessage());
        return DMakerErrorResponse.builder()
                .errorCode(INTERNAL_SERVER_ERROR)
                .errorMessage(INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }




}
