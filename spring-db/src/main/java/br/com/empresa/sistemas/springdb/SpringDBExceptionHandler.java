package br.com.empresa.sistemas.springdb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SpringDBExceptionHandler extends ResponseEntityExceptionHandler {

   @Autowired
   MessageSource messageSource;

   @Override
   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
         HttpHeaders headers, HttpStatus status, WebRequest request) {

      List<String> erros = new ArrayList<>();

      for (FieldError field : ex.getBindingResult().getFieldErrors()) {
         erros.add(messageSource.getMessage(field, LocaleContextHolder.getLocale()));
      }

      return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
   }

}