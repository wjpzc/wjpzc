package cn.xypt.exception;


import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.util.SaResult;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhangchao
 * @date 2024/4/17
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    public static final String REQUEST_PARAMS_ERROR = "请求参数异常";
    public static final String UNAUTHORIZED_ERROR = "用户未登录";

    @ExceptionHandler(Exception.class)
    public SaResult exceptionHandler(Exception e) {
        // 使用Logger.error(e)、Logger.error(e.getMessage())、Logger.error("some msg" + e)、Logger.error("some msg" + e.getMessage())
        // 都是调用的error(Object message)，这个方法都会将入参当作Object输出，不会打印堆栈信息。
        // 在使用Logger.error("first param ",e)时会调用error(String message, Throwable t)，此方法会完整的打印出错误堆栈信息。
        log.error("全局异常捕获：", e);
        return SaResult.error(e.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public SaResult notLoginExceptionHandler(Exception e) {
        return new SaResult(HttpStatus.UNAUTHORIZED.value(), UNAUTHORIZED_ERROR, e.getMessage());
    }

    // <1> 处理 form data方式调用接口校验失败抛出的异常
    @ExceptionHandler(BindException.class)
    public SaResult bindExceptionHandler(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String messages = String.join(",",
            fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toSet()));
        return new SaResult(HttpStatus.BAD_REQUEST.value(), messages, messages);
    }

    // <2> 处理 json 请求体调用接口校验失败抛出的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public SaResult methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String messages = String.join(",",
            fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toSet()));
        return new SaResult(HttpStatus.BAD_REQUEST.value(), messages, messages);
    }

    // <3> 处理单个参数校验失败抛出的异常
    @ExceptionHandler(ConstraintViolationException.class)
    public SaResult constraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        String messages = String.join(",",
            constraintViolations.stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet()));
        return new SaResult(HttpStatus.BAD_REQUEST.value(), messages, messages);
    }
}
