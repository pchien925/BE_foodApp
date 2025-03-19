package com.foodApp.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.sql.SQLException;
import java.util.Date;
import java.util.stream.Collectors;

@Hidden
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(Exception e, WebRequest request) {
        String errorMessage;
        String error;

        if (e instanceof MethodArgumentNotValidException ex) {
            error = "Dữ liệu đầu vào không hợp lệ";
            errorMessage = ex.getBindingResult().getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.joining(", "));
        } else {
            error = "Biến đường dẫn không hợp lệ";
            errorMessage = e.getMessage().substring(e.getMessage().indexOf(" ") + 1);
        }

        log.warn("Lỗi xác thực: {}", errorMessage); // Sử dụng 'log' từ @Slf4j
        return buildErrorResponse(HttpStatus.BAD_REQUEST, error, errorMessage, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        log.warn("Tài nguyên không tìm thấy: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage(), request);
    }

    @ExceptionHandler(DuplicatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicatedException(DuplicatedException e, WebRequest request) {
        log.warn("Dữ liệu trùng lặp: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage(), request);
    }

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidDataException(InvalidDataException e, WebRequest request) {
        log.warn("Dữ liệu không hợp lệ: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage(), request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException e, WebRequest request) {
        log.warn("Truy cập bị từ chối: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.getReasonPhrase(), e.getMessage(), request);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAuthenticationException(AuthenticationException e, WebRequest request) {
        log.warn("Xác thực thất bại: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.getReasonPhrase(), "Xác thực thất bại", request);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataAccessException(DataAccessException e, WebRequest request) {
        log.error("Lỗi truy cập dữ liệu: {}", e.getMessage(), e);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage(), request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e, WebRequest request) {
        log.error("Lỗi toàn vẹn dữ liệu: {}", e.getCause().getMessage(), e);
        return buildErrorResponse(HttpStatus.CONFLICT, HttpStatus.CONFLICT.getReasonPhrase(), e.getCause().getMessage(), request);
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleSQLException(SQLException e, WebRequest request) {
        String detailedMessage = String.format("Lỗi cơ sở dữ liệu: %s (SQL State: %s, Mã lỗi: %d)",
                e.getMessage(), e.getSQLState(), e.getErrorCode());
        log.error("Lỗi SQL: {}", detailedMessage, e);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Lỗi truy vấn cơ sở dữ liệu", detailedMessage, request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneralException(Exception e, WebRequest request) {
        log.error("Lỗi không xác định: {}", e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), request);
    }

    private ErrorResponse buildErrorResponse(HttpStatus status, String error, String message, WebRequest request) {
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(status.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(error)
                .message(message)
                .build();
    }
}