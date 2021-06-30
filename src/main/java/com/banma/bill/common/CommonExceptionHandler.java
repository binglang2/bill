package com.banma.bill.common;

import com.banma.bill.common.exception.BusinessException;
import com.banma.bill.common.exception.ForbiddenException;
import com.banma.bill.common.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author binglang
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object exceptionHandler(BusinessException e) {
        return Result.ofFail(e.getErrorCodeEnum());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object exceptionHandler(MissingServletRequestParameterException e) {
        return Result.ofFail(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object exceptionHandler(HttpRequestMethodNotSupportedException e) {
        return Result.ofFail(e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Object exceptionHandler(ForbiddenException e) {
        return Result.ofFail(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object exceptionHandler(NotFoundException e) {
        return Result.ofFail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object exceptionHandler(Exception e) {
        log.error("系统异常: e=", e);
        return Result.ofFail("系统异常");
    }
}
