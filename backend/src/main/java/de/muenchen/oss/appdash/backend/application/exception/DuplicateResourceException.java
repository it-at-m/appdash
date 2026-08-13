package de.muenchen.oss.appdash.backend.application.exception;

public class DuplicateResourceException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public DuplicateResourceException(
      final Class<?> entityClass, final String fieldName, final Object value) {
    super(
        String.format(
            "%s with %s '%s' already exists.", entityClass.getSimpleName(), fieldName, value));
  }

  public DuplicateResourceException(final String message) {
    super(message);
  }
}
