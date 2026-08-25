package de.muenchen.oss.appdash.backend.application.db.model;

import de.muenchen.oss.appdash.backend.Constants;
import de.muenchen.oss.appdash.backend.common.BaseLongEntity;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "type_value")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
public class TypeValue extends BaseLongEntity {
  private static final long serialVersionUID = 1L;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false, columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private TypeEnum type;

  @Column(name = "name", nullable = false, columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String name;
}
