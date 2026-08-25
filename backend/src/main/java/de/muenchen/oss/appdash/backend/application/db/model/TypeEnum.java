package de.muenchen.oss.appdash.backend.application.db.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeEnum {
  OS("os"),
  TREND("trend"),
  NUMBER_OF_USERS("number_of_users"),
  STATUS("status"),
  LANE("lane"),
  PRIORITY("priority"),
  CATEGORY("category"),
  MBUC("mbuc"),
  VISIBILITY("visibility");

  private final String value;

  TypeEnum(final String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return this.value;
  }
}
