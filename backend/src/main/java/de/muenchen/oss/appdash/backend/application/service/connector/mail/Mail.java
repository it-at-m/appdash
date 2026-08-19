package de.muenchen.oss.appdash.backend.application.service.connector.mail;

import de.muenchen.oss.appdash.backend.Constants;
import java.util.Objects;

public record Mail(String recipient, String subject, String body) {
  public Mail {
    Objects.requireNonNull(recipient, "Recipient cannot be null");
    Objects.requireNonNull(subject, "Subject cannot be null");
    Objects.requireNonNull(body, "Body cannot be null");
    if (recipient.isBlank()) {
      throw new IllegalArgumentException("Recipient cannot be blank");
    }
  }

  public String resolveRecipient() {
    return recipient.contains("@") ? recipient : recipient + Constants.MAIL_DOMAIN;
  }
}
