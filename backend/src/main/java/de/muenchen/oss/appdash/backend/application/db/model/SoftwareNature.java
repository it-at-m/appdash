package de.muenchen.oss.appdash.backend.application.db.model;

import de.muenchen.oss.appdash.backend.Constants;
import de.muenchen.oss.appdash.backend.common.BaseLongEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "software_nature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareNature extends BaseLongEntity {
  private static final long serialVersionUID = 1L;

  @Column(name = "name", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String name;
}
