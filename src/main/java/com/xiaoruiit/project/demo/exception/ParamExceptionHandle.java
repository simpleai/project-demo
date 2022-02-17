package com.xiaoruiit.project.demo.exception;

import com.xiaoruiit.project.demo.common.ApiCodes;
import com.xiaoruiit.project.demo.common.Result;
import com.xiaoruiit.project.demo.utils.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * Description 参数异常处理
 *
 * @author - herry
 * @create - 2022/02/17
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class ParamExceptionHandle {

    /**
     * 处理Get请求参数格式错误 @RequestParam
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        printErrorMessage(request, e);
        return Result.error(ApiCodes.PARAM_ERROR.getCode(), e.getMessage());
    }

    /**
     * 处理Get请求参数格式错误 @RequestParam上validate失败后抛出的异常
     * 会被RequestParam的验null拦截，抛MissingServletRequestParameterException
     * NotBlank仍可校验是否为空串，抛ConstraintViolationException
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result handleValidException(ConstraintViolationException e) {
        log.error("参数校验错误, 错误栈: ", e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            return Result.error(ApiCodes.PARAM_ERROR.getCode(), constraintViolation.getPropertyPath() + constraintViolation.getMessage());
        }
        return Result.error(ApiCodes.PARAM_ERROR.getCode(),e.getMessage());
    }

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     */
    @ExceptionHandler(value = BindException.class)
    public Result handleValidException(BindException e) {
        log.error("参数校验错误, 错误栈: ", e);
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return Result.error(ApiCodes.PARAM_ERROR.getCode(),message);
    }

    private void printErrorMessage(HttpServletRequest request, Exception exception) {
        String parameter = JSON.toJSONString(request.getParameterMap());
        exception.printStackTrace();
        log.error("服务异常: url: {}, message: {}, parameter: {}", request.getRequestURL(), exception.getMessage(), parameter);
    }

}
