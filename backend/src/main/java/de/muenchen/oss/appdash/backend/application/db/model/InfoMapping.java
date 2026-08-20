package de.muenchen.oss.appdash.backend.application.db.model;

import de.muenchen.oss.appdash.backend.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public record InfoMapping(
    @Column(name = "mapping_key", nullable = false) String key,
    @Column(name = "mapping_value", columnDefinition = Constants.COLUMN_TYPE_TEXT) String value)
    implements Serializable {
  private static final long serialVersionUID = 1L;
}
