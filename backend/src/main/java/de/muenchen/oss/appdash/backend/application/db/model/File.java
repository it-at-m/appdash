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
@Table(name = "file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File extends BaseLongEntity {
  private static final long serialVersionUID = 1L;

  @Column(name = "size", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String size;

  @Column(name = "version", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String version;

  @Column(name = "cnt_files")
  private Integer cntFiles;

  @Column(name = "date_updated")
  private Instant dateUpdated;

  @CreationTimestamp
  @Column(name = "timestamp_created")
  private Instant timestampCreated;

  @UpdateTimestamp
  @Column(name = "timestamp_updated")
  private Instant timestampUpdated;

  @Column(name = "file_name", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String fileName;

  @ManyToOne
  @JoinColumn(name = "app_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private App app;

  @ManyToOne
  @JoinColumn(name = "file_type_id")
  private FileType fileType;
}
