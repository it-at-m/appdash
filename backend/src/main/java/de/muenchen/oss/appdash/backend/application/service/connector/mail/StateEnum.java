package de.muenchen.oss.appdash.backend.application.service.connector.mail;

public enum StateEnum {
  ERLEDIGT("erledigt"),
  IN_REVIEW("in review"),
  NEU("neu");

  private final String value;

  StateEnum(final String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
