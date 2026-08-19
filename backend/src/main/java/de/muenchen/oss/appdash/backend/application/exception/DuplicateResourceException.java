package de.muenchen.oss.appdash.backend.application.exception;

public class DuplicateResourceException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public DuplicateResourceException(final String message) {
    super(message);
  }

  public DuplicateResourceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public DuplicateResourceException(
      final Class<?> entityClass, final String name, final Object value) {
    super(
        String.format("%s with %s '%s' already exists.", entityClass.getSimpleName(), name, value));
  }
}
