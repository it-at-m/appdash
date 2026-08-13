package de.muenchen.oss.appdash.backend.application.db.model;

import de.muenchen.oss.appdash.backend.common.BaseLongEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseLongEntity {
  private static final long serialVersionUID = 1L;

  @Column(name = "name", columnDefinition = "TEXT")
  private String name;
}
