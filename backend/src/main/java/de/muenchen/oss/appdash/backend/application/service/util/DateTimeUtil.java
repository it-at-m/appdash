package de.muenchen.oss.appdash.backend.application.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtil {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static final DateTimeFormatter STANDARD_DATE =
      DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY);

  public static final DateTimeFormatter STANDARD_DATE_TIME =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.GERMANY);

  private static final DateTimeFormatter FLEXIBLE_DATE_TIME =
      new DateTimeFormatterBuilder()
          .appendPattern("yyyy-MM-dd")
          .optionalStart()
          .appendLiteral('T')
          .appendPattern("HH:mm:ss")
          .optionalStart()
          .appendFraction(ChronoField.MILLI_OF_SECOND, 0, 9, true)
          .optionalEnd()
          .optionalStart()
          .appendOffset("+HH:mm", "Z")
          .optionalEnd()
          .optionalEnd()
          .toFormatter(Locale.ROOT);

  // Formats date into "dd.MM.yyyy"
  public static String formatDate(final Object dateObj) {
    if (dateObj == null) {
      return "-";
    }
    if (dateObj instanceof Date date) {
      return STANDARD_DATE.format(date.toInstant().atZone(ZoneId.systemDefault()));
    }
    if (dateObj instanceof TemporalAccessor temporalAccessor) {
      return STANDARD_DATE.format(temporalAccessor);
    }
    return dateObj.toString();
  }

  // Parses ISO date and time strings to instant
  public static Instant toInstant(final String input) {
    if (input == null || input.isBlank()) {
      return null;
    }

    final String normalized = input.trim().replace(' ', 'T');
    final TemporalAccessor temporalAccessor =
        FLEXIBLE_DATE_TIME.parseBest(
            normalized,
            Instant::from,
            ta -> LocalDateTime.from(ta).atZone(ZoneId.systemDefault()).toInstant(),
            ta -> LocalDate.from(ta).atStartOfDay(ZoneId.systemDefault()).toInstant());

    return Instant.from(temporalAccessor);
  }

  // Converts a list of objects to a json string
  public static String toJsonString(final Object object) {
    if (object == null) {
      return "null";
    }
    try {
      return OBJECT_MAPPER.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error("Failed to serialize object to JSON: {}", e.getMessage());
      return "[]";
    }
  }
}
