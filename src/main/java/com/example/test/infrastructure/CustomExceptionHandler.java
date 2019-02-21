package com.example.test.infrastructure;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

   @ExceptionHandler(NestedRuntimeException.class)
   public ResponseEntity<Map<String, Object>> processDataError(final NestedRuntimeException ex, final HttpServletRequest request) {
        if (ex.getMostSpecificCause() instanceof ConstraintViolationException) {
            final ConstraintViolationException cex = (ConstraintViolationException) ex.getMostSpecificCause();
            final Map<String, Object> errorInfo = json(cex, HttpStatus.BAD_REQUEST, request);

            if (cex.getConstraintViolations() != null && !cex.getConstraintViolations().isEmpty()) {
                final StringBuilder message = new StringBuilder();
                cex.getConstraintViolations().forEach(c ->
                        message.append(c.getPropertyPath()).append(' ').append(c.getMessage()).append('\n')
                );
                errorInfo.put("message", message.toString());
            }

            return ResponseEntity.badRequest().body(errorInfo);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(json(ex.getMostSpecificCause(), HttpStatus.INTERNAL_SERVER_ERROR, request));
    }

    private Map<String, Object> json(final Throwable error, final HttpStatus status, final HttpServletRequest request) {
        final Map<String, Object> errorInfo = new LinkedHashMap<>();

        errorInfo.put("timestamp", System.currentTimeMillis());
        errorInfo.put("status", status.value());
        errorInfo.put("error", error.getClass().getSimpleName());
        errorInfo.put("message", error.getMessage());
        errorInfo.put("path", request.getServletPath());

        return errorInfo;
    }

}