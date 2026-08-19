package de.muenchen.oss.appdash.backend.application.db.model;

import de.muenchen.oss.appdash.backend.Constants;
import de.muenchen.oss.appdash.backend.common.BaseLongEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "scan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Scan extends BaseLongEntity {
  private static final long serialVersionUID = 1L;

  @Column(name = "scan_key", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String scanKey;

  @Column(name = "score")
  private Integer score;

  @CreationTimestamp
  @Column(name = "timestamp_created")
  private Instant timestampCreated;

  @UpdateTimestamp
  @Column(name = "timestamp_updated")
  private Instant timestampUpdated;

  @ManyToOne
  @JoinColumn(name = "file_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private File file;

  @ManyToOne
  @JoinColumn(name = "provider_id")
  private Provider provider;
}
