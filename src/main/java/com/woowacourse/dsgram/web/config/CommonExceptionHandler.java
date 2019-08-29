package com.woowacourse.dsgram.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public String handler(RuntimeException e, Model model) {
        log.warn("{}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }


}
