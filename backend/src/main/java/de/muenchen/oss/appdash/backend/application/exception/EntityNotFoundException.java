package de.muenchen.oss.appdash.backend.application.exception;

public class EntityNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public EntityNotFoundException(final String message) {
    super(message);
  }

  public EntityNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public EntityNotFoundException(final Class<?> entityClass, final Object id) {
    super(String.format("%s with ID '%s' was not found.", entityClass.getSimpleName(), id));
  }
}
