package de.muenchen.oss.appdash.backend.application.db.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LookupType {
  OS("os"),
  TREND("trend"),
  NUMBER_OF_USERS("number_of_users"),
  STATUS("status"),
  LANE("lane"),
  REFERAT("referat"),
  CLIENT("client"),
  PRIORITY("priority"),
  CATEGORY("category"),
  MBUC("mbuc"),
  SOURCE("source"),
  VISIBILITY("visibility");

  private final String value;

  LookupType(final String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return this.value;
  }
}
