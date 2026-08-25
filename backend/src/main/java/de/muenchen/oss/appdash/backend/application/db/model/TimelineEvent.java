package de.muenchen.oss.appdash.backend.application.db.model;

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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "timeline_event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimelineEvent extends BaseLongEntity {
  private static final long serialVersionUID = 1L;

  @Column(name = "type")
  private Integer type;

  @Column(name = "timestamp_start", nullable = false)
  private Instant timestampStart;

  @Column(name = "timestamp_end")
  private Instant timestampEnd;

  @ManyToOne
  @JoinColumn(name = "status_id", nullable = false)
  private TypeValue status;

  @ManyToOne
  @JoinColumn(name = "process_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Process process;
}
