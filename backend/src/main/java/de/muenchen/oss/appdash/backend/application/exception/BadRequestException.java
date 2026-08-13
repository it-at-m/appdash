package de.muenchen.oss.appdash.backend.application.exception;

public class BadRequestException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public BadRequestException(final String message) {
    super(message);
  }
}
