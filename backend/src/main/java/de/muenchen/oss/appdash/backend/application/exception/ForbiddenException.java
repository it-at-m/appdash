package de.muenchen.oss.appdash.backend.application.exception;

public class ForbiddenException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public ForbiddenException(final String message) {
    super(message);
  }

  public ForbiddenException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
