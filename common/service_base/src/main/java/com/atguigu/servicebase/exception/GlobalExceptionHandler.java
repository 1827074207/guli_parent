package com.atguigu.servicebase.exception;


import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    //全局异常处理
    @ExceptionHandler(Exception.class)
    public R err(Exception exception){
        exception.printStackTrace();
        return R.error().message("执行全局异常处理类");
    }

    //特定异常处理
    //ArithmeticException是除零异常 执行顺序会是特定异常高于全局异常
    @ExceptionHandler(ArithmeticException.class)
    public R err(ArithmeticException exception){
        exception.printStackTrace();
        return R.error().message("执行全局异常处理类");
    }
    //    try{
    //        你要捕获异常的代码
    //    }catch(Exception e){
    //        然后自己手动把自定义的异常new出来
    //        throw new GuliException(20001,"捕获自定义异常");
    //    }
    //自定义异常处理
    @ExceptionHandler(GuliException.class)
    public R err(GuliException exception){
        log.error(exception.getMessage());
        exception.printStackTrace();
        return R.error().code(exception.getCode()).message(exception.getMsg());
    }


}
