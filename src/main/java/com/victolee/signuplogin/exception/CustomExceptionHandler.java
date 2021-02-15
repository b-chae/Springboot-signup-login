package com.victolee.signuplogin.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public ModelAndView handleError(UserAlreadyExistsException e){
        ModelAndView modelAndView =  new ModelAndView("redirect:/user/signup?usererror");
        return modelAndView;
    }

    @ExceptionHandler
    public ModelAndView handleError(WrongPasswordConfirmException e){
        ModelAndView modelAndView =  new ModelAndView("redirect:/user/signup?passworderror");
        return modelAndView;
    }
}
