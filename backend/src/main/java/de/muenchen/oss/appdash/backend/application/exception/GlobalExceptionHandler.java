package de.muenchen.oss.appdash.backend.application.exception;

import de.muenchen.oss.appdash.backend.openapi.model.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
  public ResponseEntity<ErrorResponse> handleBadRequest(final BadRequestException exception) {
    log.warn("Bad request: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(buildErrorResponse("BAD_REQUEST", exception.getMessage(), null));
  }

  // FORBIDDEN (403)
  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ErrorResponse> handleForbidden(final ForbiddenException exception) {
    log.warn("Access forbidden: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(buildErrorResponse("FORBIDDEN", exception.getMessage(), null));
  }

  // NOT FOUND (404)
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFound(
      final EntityNotFoundException exception) {
    log.warn("Resource not found: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(buildErrorResponse("NOT_FOUND", exception.getMessage(), null));
  }

  // CONFLICT (409)
  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateResource(
      final DuplicateResourceException exception) {
    log.warn("Resource conflict: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(buildErrorResponse("CONFLICT", exception.getMessage(), null));
  }

  // SERVICE UNAVAILABLE (503)
  @ExceptionHandler(ExternalServiceException.class)
  public ResponseEntity<ErrorResponse> handleExternalServiceError(
      final ExternalServiceException exception) {
    log.error("External service failure: {}", exception.getMessage(), exception);
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(buildErrorResponse("SERVICE_UNAVAILABLE", exception.getMessage(), null));
  }

  // SPRING DTO VALIDATION FAILURES (400)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      final MethodArgumentNotValidException exception) {
    final Map<String, Object> details = new HashMap<>();

    for (final FieldError error : exception.getBindingResult().getFieldErrors()) {
      final String field = error.getField();
      final String message = error.getDefaultMessage();

      details.compute(
          field,
          (k, v) -> {
            if (v == null) {
              final List<String> list = new ArrayList<>();
              list.add(message);
              return list;
            }
            if (v instanceof List<?> list) {
              @SuppressWarnings("unchecked")
              final List<String> strList = (List<String>) list;
              strList.add(message);
              return strList;
            }
            return v;
          });
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
      final ResponseStatusException exception) {
    log.warn("HTTP Exception [{}]: {}", exception.getStatusCode(), exception.getReason());
    return ResponseEntity.status(exception.getStatusCode())
        .body(
            buildErrorResponse(exception.getStatusCode().toString(), exception.getReason(), null));
  }

  // UNCAUGHT SYSTEM CRASHES (500)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(final Exception exception) {
    final String traceId = getOrGenerateTraceId();
    log.error("Unhandled server exception [Trace ID: {}]", traceId, exception);

    final ErrorResponse errorResponse =
        buildErrorResponse(
            "INTERNAL_SERVER_ERROR", "An unexpected error occurred. Please contact support.", null);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  private ErrorResponse buildErrorResponse(
      final String error, final String message, final Map<String, Object> details) {
    final ErrorResponse response = new ErrorResponse();
    response.setError(error);
    response.setMessage(message);
    response.setDetails(details);
    response.setTimestamp(OffsetDateTime.now());
    response.setTraceId(getOrGenerateTraceId());
    return response;
  }

  private String getOrGenerateTraceId() {
    String traceId = MDC.get("traceId");
    if (traceId == null) {
      traceId = MDC.get("X-B3-TraceId");
    }
    return traceId != null ? traceId : UUID.randomUUID().toString();
  }
}
