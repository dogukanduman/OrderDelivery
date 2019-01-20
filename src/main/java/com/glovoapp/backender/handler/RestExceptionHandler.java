package com.glovoapp.backender.handler;

import com.glovoapp.backender.dto.ErrorDetail;
import com.glovoapp.backender.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Dogukan Duman on 16.01.2019.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle IllegalArgumentException
     *
     * @param ile
     * @param request
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ile, HttpServletRequest request) {
        logger.debug("IllegalArgumentException is handled");
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Binary Data Missing");
        errorDetail.setDetail(ile.getMessage());

        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle MethodArgumentNotValidException
     *
     * @param manve
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manve, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.debug("MethodArgumentNotValidException is handled");
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Validation Failed");
        errorDetail.setDetail(manve.getMessage());

        return handleExceptionInternal(manve, errorDetail, headers, status, request);
    }

    /**
     * Handle HttpMessageNotReadableException
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logger.debug("HttpMessageNotReadableException is handled");
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Message Not Readable");
        errorDetail.setDetail(ex.getMessage());
        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }

    /**
     * Handle ResourceNotFoundException
     *
     * @param rnfe
     * @param request
     * @return
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe, HttpServletRequest request) {

        logger.debug("ResourceNotFoundException is handled");
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(rnfe.getMessage());

        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }
}