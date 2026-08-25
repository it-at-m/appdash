package de.muenchen.oss.appdash.backend.application.db.model;

import de.muenchen.oss.appdash.backend.Constants;
import de.muenchen.oss.appdash.backend.common.BaseLongEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "ai_scan_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AiScanReport extends BaseLongEntity {
  private static final long serialVersionUID = 1L;

  @CreationTimestamp
  @Column(name = "timestamp_created")
  private Instant timestampCreated;

  @UpdateTimestamp
  @Column(name = "timestamp_updated")
  private Instant timestampUpdated;

  @Column(name = "libraries", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String libraries;

  @Column(name = "permissions", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String permissions;

  @Column(name = "domains", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String domains;

  @Column(name = "general", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String general;

  @Column(name = "summary", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String summary;

  @OneToOne
  @JoinColumn(name = "scan_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Scan scan;
}
