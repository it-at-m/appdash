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

  @CreationTimestamp
  @Column(name = "timestamp_created")
  private Instant timestampCreated;

  @UpdateTimestamp
  @Column(name = "timestamp_updated")
  private Instant timestampUpdated;

  @Column(name = "scan_key", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String scanKey;

  @Column(name = "size", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String size;

  @Column(name = "version", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String version;

  @Column(name = "score")
  private Integer score;

  @Column(name = "cnt_files")
  private Integer cntFiles;

  @ManyToOne
  @JoinColumn(name = "file_type_id")
  private TypeValue fileType;

  @ManyToOne
  @JoinColumn(name = "provider_id")
  private TypeValue provider;

  @ManyToOne
  @JoinColumn(name = "app_process_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private AppProcess appProcess;
}
