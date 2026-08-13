package de.muenchen.oss.appdash.backend.security;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Each possible authority in this project is represented by a constant in this class. The constants
 * are used within the {@link org.springframework.stereotype.Controller} or {@link
 * org.springframework.stereotype.Service} classes in the method security annotations (e.g. {@link
 * PreAuthorize}).
 */
public final class Authorities {
  // Role based auth (default)
  public static final String ADMIN = "hasAnyRole('admin')";
  public static final String BETRIEB = "hasAnyRole('betrieb')";
  public static final String USER = "hasAnyRole('user')";

  private Authorities() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
}
