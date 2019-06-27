/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.controller.exceptions;

import com.tsoft.exceptions.BusinessException;
import com.tsoft.exceptions.NoSuchCategorieException;
import com.tsoft.utils.StringUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(
    basePackages = {"com.tsoft"}
)
public class CustomHandlerExceptionResolver extends ResponseEntityExceptionHandler {
    public CustomHandlerExceptionResolver() {
    }

    @ExceptionHandler({Exception.class, BusinessException.class, NoSuchCategorieException.class})
    public ResponseEntity<Object> handleBusinessException(Exception ex, WebRequest request) {
        Logger.getLogger(CustomHandlerExceptionResolver.class.getName()).log(Level.SEVERE, (String)null, ex);
        return !(ex instanceof BusinessException) && !(ex instanceof NoSuchCategorieException) ? new ResponseEntity(ex.getMessage() != null ? ex.getMessage() : StringUtils.getStackTrace(ex), HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
