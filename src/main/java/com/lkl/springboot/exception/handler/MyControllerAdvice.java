package com.lkl.springboot.exception.handler;

import java.net.BindException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkl.springboot.exception.RestException;
import com.lkl.springboot.response.SpringBootResponse;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MyControllerAdvice {

    @ExceptionHandler(RestException.class)
    @ResponseBody
    public SpringBootResponse handRestException(RestException e, HttpServletResponse httpResponse) {
        String msg = e.getMessage();
        if (msg.indexOf(";") != -1) {
            String[] errors = msg.split(";");
            httpResponse.setStatus(Integer.parseInt(errors[0]));
            return new SpringBootResponse(Integer.parseInt(errors[0]), errors[1]);
        }

        return new SpringBootResponse(HttpStatus.FORBIDDEN.value(), "位置错误");
    }

    /**
     * 如果和@ExceptionHandler 分开存在，则放在父类中优先执行 {@link MyExceptionHandler}
     * @param r
     * @param request
     * @param response
     * @return
     */
    // @ExceptionHandler(RuntimeException.class)
    public String handleRunTimeException(RuntimeException r, HttpServletResponse response) {
        System.out.println("r.getClass():" + r.getClass());
        System.out.println(" MyExceptionHandler:" + r.getMessage());
        return " @ControllerAdvice Handle Error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException,
                                          HttpServletResponse httpResponse) {
        System.out.println("---handleValidationException---");
        List<ObjectError> errorList = methodArgumentNotValidException.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(errorList)) {
            ObjectError e = errorList.get(0);
            System.out.println("code：" + e.getCode() + ";msg:" + e.getDefaultMessage());

        }

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        System.out.println("----handleHttpMessageNotReadableException--");
    }

    @ExceptionHandler(BindException.class)
    public void handleBindException(BindException bindException) {
        System.out.println("----handleBindException--");
    }

    /**
     * 在RequestMapper之前数据绑定
     *   @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    @ModelAttribute
    public Object modelAttributeMethod() {
        return null;
    }
     * @param binder
     */

}
