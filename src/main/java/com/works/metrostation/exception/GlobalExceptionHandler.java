package com.works.metrostation.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorObject> entityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ErrorObject message = new ErrorObject(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        LOG.error(ex.getMessage());
        return new ResponseEntity<ErrorObject>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ErrorObject> cardNotFoundException(CardNotFoundException ex, WebRequest request) {
        ErrorObject message = new ErrorObject(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        LOG.error(ex.getMessage());
        return new ResponseEntity<ErrorObject>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorObject> accessDeniedException(AccessDeniedException ex, WebRequest request) {
        ErrorObject message = new ErrorObject(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        LOG.error(ex.getMessage());
        return new ResponseEntity<ErrorObject>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorObject> usernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        ErrorObject message = new ErrorObject(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        LOG.error(ex.getMessage());
        return new ResponseEntity<ErrorObject>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorObject> iOException(IOException ex, WebRequest request) {
        ErrorObject message = new ErrorObject(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        LOG.error(ex.getMessage());
        return new ResponseEntity<ErrorObject>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ErrorObject> servletException(ServletException ex, WebRequest request) {
        ErrorObject message = new ErrorObject(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        LOG.error(ex.getMessage());
        return new ResponseEntity<ErrorObject>(message, HttpStatus.FORBIDDEN);
    }

/*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        ErrorObject message = new ErrorObject(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        LOG.error(ex.getMessage());
        return new ResponseEntity<ErrorObject>(message, HttpStatus.BAD_REQUEST);
    }
*/
    @ExceptionHandler(CardAlreadyExistsException.class)
    public ResponseEntity<ErrorObject> cardAlreadyExistsException(CardAlreadyExistsException ex, WebRequest request) {
        ErrorObject message = new ErrorObject(
                HttpStatus.CONFLICT.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        LOG.error(ex.getMessage());
        return new ResponseEntity<ErrorObject>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {

        List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError message = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false),
                errors);
        LOG.error("Validation Error" + errors);
        return new ResponseEntity<ApiError>(message, HttpStatus.BAD_REQUEST);
    }

    /*
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false),
                errors);

        return handleExceptionInternal(ex, apiError, headers, HttpStatus.valueOf(apiError.getStatusCode()), request);
    }
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorObject message = new ErrorObject(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        LOG.error(ex.getMessage());
        return new ResponseEntity<ErrorObject>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
