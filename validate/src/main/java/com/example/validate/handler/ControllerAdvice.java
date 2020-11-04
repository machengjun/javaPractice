package com.example.validate.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * ControllerAdvice
 * 全局异常捕捉处理
 *
 * @author 马成军
 */
@org.springframework.web.bind.annotation.RestControllerAdvice
public class ControllerAdvice {


    /*  Validated 数据校验处理 */
    @ExceptionHandler({BindException.class, ConstraintViolationException.class})
    public ResponseEntity validatorExceptionHandler(Exception e) {
        String errMsg = e instanceof BindException ? msgConvertor(((BindException) e).getBindingResult())
                : msgConvertor(((ConstraintViolationException) e).getConstraintViolations());

        return new ResponseEntity(errMsg, HttpStatus.BAD_REQUEST);
    }

    /*  RequestBody Validated 数据校验处理 */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity ExceptionHandler(Exception exception) {
        BindingResult bindResult = ((MethodArgumentNotValidException) exception).getBindingResult();
        String errMsg = msgConvertor(bindResult);
        return new ResponseEntity(errMsg, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 校验消息转换拼接
     *
     * @param bindingResult
     * @return
     */
    public static String msgConvertor(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        fieldErrors.forEach(fieldError -> sb.append(fieldError.getDefaultMessage()).append(","));

        return sb.deleteCharAt(sb.length() - 1).toString().toLowerCase();
    }

    private String msgConvertor(Set<ConstraintViolation<?>> constraintViolations) {
        StringBuilder sb = new StringBuilder();
        constraintViolations.forEach(violation -> sb.append(violation.getMessage()).append(","));

        return sb.deleteCharAt(sb.length() - 1).toString().toLowerCase();
    }

}

