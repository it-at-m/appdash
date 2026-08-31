package de.muenchen.oss.appdash.backend.common;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Component;

@Component
public class DateTimeMapper {
  public OffsetDateTime toOffsetDateTime(final Instant instant) {
    return instant != null ? instant.atOffset(ZoneOffset.UTC) : null;
  }

  public Instant toInstant(final OffsetDateTime offsetDateTime) {
    return offsetDateTime != null ? offsetDateTime.toInstant() : null;
  }
}
