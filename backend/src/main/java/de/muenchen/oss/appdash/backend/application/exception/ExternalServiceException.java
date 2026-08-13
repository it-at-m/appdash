package de.muenchen.oss.appdash.backend.application.exception;

public class ExternalServiceException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public ExternalServiceException(final String serviceName, final String message) {
    super(String.format("[%s Error] %s", serviceName, message));
  }

  public ExternalServiceException(
      final String serviceName, final String message, final Throwable cause) {
    super(String.format("[%s Error] %s", serviceName, message), cause);
  }
}
