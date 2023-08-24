package com.example.holidayswap.config;

import com.example.holidayswap.domain.exception.ApiError;
import com.example.holidayswap.domain.exception.ResourceNotFoundException;
import com.example.holidayswap.domain.exception.VerificationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    //400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class})
    public ResponseEntity<Object> handleBindException(BindException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({TypeMismatchException.class})
    public ResponseEntity<Object> handlTypeMismatchException(TypeMismatchException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();

        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestPartException.class})
    public ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final String error = ex.getRequestPartName() + " part is missing";
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final String error = ex.getParameterName() + " parameter is missing";
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final String error = ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName();

        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final List<String> errors = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


//    //401
//    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler({
//            AuthenticationException.class,
//            VerificationException.class
//    })
//    public ResponseEntity<Object> authenticationException(Exception ex) {
//        log.info(ex.getClass().getName());
//        log.error("error", ex);
//        //
//        final ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(), "You do not have permission to access this resource.");
//        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
//    }
//
//
//    // 403
//    @ResponseStatus(value = HttpStatus.FORBIDDEN)
//    @ExceptionHandler({AccessDeniedException.class})
//    public ResponseEntity<Object> handleAccessDeniedException(Exception ex) {
//        log.info(ex.getClass().getName());
//        log.error("error", ex);
//        //
//        final ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(), "You do not have permission to access this resource.");
//        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
//    }


    // 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoHandlerFoundException.class})
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleResourceNotFoundException(final ResourceNotFoundException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final String error = "The requested resource could not be found.";

        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    // 405
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t).append(" "));

        final ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    // 415
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(" "));

        final ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    // 500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            SQLException.class,
            DataAccessException.class
    })
    public ResponseEntity<Object> handleQueryException(Exception ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //

        final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), " error occurred");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}