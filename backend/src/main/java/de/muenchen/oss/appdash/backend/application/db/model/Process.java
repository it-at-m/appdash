package de.muenchen.oss.appdash.backend.application.db.model;

import de.muenchen.oss.appdash.backend.Constants;
import de.muenchen.oss.appdash.backend.common.BaseLongEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "process")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Process extends BaseLongEntity {
  private static final long serialVersionUID = 1L;

  @CreationTimestamp
  @Column(name = "timestamp_created")
  private Instant timestampCreated;

  @UpdateTimestamp
  @Column(name = "timestamp_updated")
  private Instant timestampUpdated;

  @Column(name = "timestamp_status_updated")
  private Instant timestampStatusUpdated;

  @Column(name = "timestamp_store_updated")
  private Instant timestampStoreUpdated;

  @Column(name = "timestamp_focus")
  private Instant timestampFocus;

  @Column(name = "timestamp_control_updated")
  private Instant timestampControlUpdated;

  @Column(name = "description", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String description;

  @Column(name = "mdm", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String mdm;

  @Column(name = "is_pilot")
  private Boolean isPilot;

  @Column(name = "is_critical")
  private Boolean isCritical;

  @ManyToOne
  @JoinColumn(name = "os_id")
  private LookupValue os;

  @ManyToOne
  @JoinColumn(name = "trend_id")
  private LookupValue trend;

  @ManyToOne
  @JoinColumn(name = "number_of_users_id")
  private LookupValue numberOfUsers;

  @ManyToOne
  @JoinColumn(name = "status_id")
  private LookupValue status;

  @ManyToOne
  @JoinColumn(name = "lane_id")
  private LookupValue lane;

  @ManyToOne
  @JoinColumn(name = "referat_id")
  private LookupValue referat;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private LookupValue client;

  @ManyToOne
  @JoinColumn(name = "app_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private App app;

  @ElementCollection
  @CollectionTable(name = "process_customer_info", joinColumns = @JoinColumn(name = "process_id"))
  private Set<InfoMapping> customerInfos = new HashSet<>();

  @ElementCollection
  @CollectionTable(name = "process_url_info", joinColumns = @JoinColumn(name = "process_id"))
  private Set<InfoMapping> urlInfos = new HashSet<>();

  @ElementCollection
  @CollectionTable(name = "process_rsm_key_info", joinColumns = @JoinColumn(name = "process_id"))
  private Set<InfoMapping> rsmKeyInfos = new HashSet<>();
}
