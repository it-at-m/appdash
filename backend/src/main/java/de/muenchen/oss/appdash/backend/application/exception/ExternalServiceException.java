package de.muenchen.oss.appdash.backend.application.exception;

public class ExternalServiceException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public ExternalServiceException(final String message) {
    super(message);
  }

  public ExternalServiceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ExternalServiceException(final String name, final String message) {
    super(String.format("[%s Error] %s", name, message));
  }

  public ExternalServiceException(final String name, final String message, final Throwable cause) {
    super(String.format("[%s Error] %s", name, message), cause);
  }
}
