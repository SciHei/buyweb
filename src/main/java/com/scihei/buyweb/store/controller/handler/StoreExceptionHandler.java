package com.scihei.buyweb.store.controller.handler;

import com.scihei.buyweb.common.HttpResultVO;
import com.scihei.buyweb.common.service.exception.ServiceException;
import com.scihei.buyweb.store.controller.StoreController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
@Slf4j
@ControllerAdvice(assignableTypes = {StoreController.class})
public class StoreExceptionHandler {
    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public HttpResultVO<Boolean> serviceExceptionHandler(ServiceException e){
        log.warn(e.toString());
        return HttpResultVO.fail(false, e.getResult());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public HttpResultVO<Boolean> exceptionHandler(Exception e){
        log.warn(e.toString());
        return HttpResultVO.fail(false, e.getMessage());
    }
}
