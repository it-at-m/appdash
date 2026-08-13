package de.muenchen.oss.appdash.backend.application.exception;

import de.muenchen.oss.appdash.backend.openapi.model.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  // BAD REQUEST (400)
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequest(final BadRequestException e) {
    log.warn("Bad request: {}", e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(buildErrorResponse("BAD_REQUEST", e.getMessage(), null));
  }

  // FORBIDDEN (403)
  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ErrorResponse> handleForbidden(final ForbiddenException e) {
    log.warn("Access forbidden: {}", e.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(buildErrorResponse("FORBIDDEN", e.getMessage(), null));
  }

  // NOT FOUND (404)
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFound(final EntityNotFoundException e) {
    log.warn("Resource not found: {}", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(buildErrorResponse("NOT_FOUND", e.getMessage(), null));
  }

  // CONFLICT (409)
  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateResource(final DuplicateResourceException e) {
    log.warn("Resource conflict: {}", e.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(buildErrorResponse("CONFLICT", e.getMessage(), null));
  }

  // SERVICE UNAVAILABLE (503)
  @ExceptionHandler(ExternalServiceException.class)
  public ResponseEntity<ErrorResponse> handleExternalServiceError(
      final ExternalServiceException e) {
    log.error("External service failure: {}", e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(buildErrorResponse("SERVICE_UNAVAILABLE", e.getMessage(), null));
  }

  // SPRING DTO VALIDATION FAILURES (400)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      final MethodArgumentNotValidException e) {
    final Map<String, Object> details = new HashMap<>();
    for (final FieldError error : e.getBindingResult().getFieldErrors()) {
      details.put(error.getField(), error.getDefaultMessage());
    }

    log.warn("Validation failed for {} field(s)", details.size());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            buildErrorResponse(
                "VALIDATION_FAILED", "Validation failed for request payload", details));
  }

  // SPRING ResponseStatusException FALLBACK
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponse> handleResponseStatusException(
      final ResponseStatusException e) {
    log.warn("HTTP Exception [{}]: {}", e.getStatusCode(), e.getReason());
    return ResponseEntity.status(e.getStatusCode())
        .body(buildErrorResponse(e.getStatusCode().toString(), e.getReason(), null));
  }

  // UNCAUGHT SYSTEM CRASHES (500)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(final Exception ex) {
    final String traceId = UUID.randomUUID().toString();
    log.error("Unhandled server exception [Trace ID: {}]", traceId, ex);

    final ErrorResponse errorResponse =
        buildErrorResponse(
            "INTERNAL_SERVER_ERROR", "An unexpected error occurred. Please contact support.", null);
    errorResponse.setTraceId(traceId);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  private ErrorResponse buildErrorResponse(
      final String error, final String message, final Map<String, Object> details) {
    final ErrorResponse response = new ErrorResponse();
    response.setError(error);
    response.setMessage(message);
    response.setDetails(details);
    response.setTimestamp(OffsetDateTime.now());
    response.setTraceId(UUID.randomUUID().toString());
    return response;
  }
}
