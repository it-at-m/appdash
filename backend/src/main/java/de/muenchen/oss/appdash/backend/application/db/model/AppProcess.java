package de.muenchen.oss.appdash.backend.application.db.model;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

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
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity
@Table(name = "app_process")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class AppProcess extends BaseLongEntity {
  private static final long serialVersionUID = 1L;

  @CreationTimestamp
  @Column(name = "timestamp_created")
  @NotAudited
  private Instant timestampCreated;

  @UpdateTimestamp
  @Column(name = "timestamp_updated")
  @NotAudited
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
  @Audited(targetAuditMode = NOT_AUDITED)
  private TypeValue os;

  @ManyToOne
  @JoinColumn(name = "trend_id")
  @Audited(targetAuditMode = NOT_AUDITED)
  private TypeValue trend;

  @ManyToOne
  @JoinColumn(name = "number_of_users_id")
  @Audited(targetAuditMode = NOT_AUDITED)
  private TypeValue numberOfUsers;

  @ManyToOne
  @JoinColumn(name = "status_id")
  @Audited(targetAuditMode = NOT_AUDITED)
  private TypeValue status;

  @ManyToOne
  @JoinColumn(name = "lane_id")
  @Audited(targetAuditMode = NOT_AUDITED)
  private TypeValue lane;

  @ManyToOne
  @JoinColumn(name = "viv_id")
  @Audited(targetAuditMode = NOT_AUDITED)
  private TypeValue viv;

  @ManyToOne
  @JoinColumn(name = "app_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private App app;

  @ElementCollection
  @CollectionTable(
      name = "app_process_customer_info",
      joinColumns = @JoinColumn(name = "app_process_id"))
  private Set<InfoMapping> customerInfos = new HashSet<>();

  @ElementCollection
  @CollectionTable(
      name = "app_process_url_info",
      joinColumns = @JoinColumn(name = "app_process_id"))
  private Set<InfoMapping> urlInfos = new HashSet<>();

  @ElementCollection
  @CollectionTable(
      name = "app_process_rsm_key_info",
      joinColumns = @JoinColumn(name = "app_process_id"))
  private Set<InfoMapping> rsmKeyInfos = new HashSet<>();

  @ElementCollection
  @CollectionTable(
      name = "app_process_comment_info",
      joinColumns = @JoinColumn(name = "app_process_id"))
  private Set<InfoMapping> commentInfos = new HashSet<>();

  @ElementCollection
  @CollectionTable(
      name = "app_process_license_info",
      joinColumns = @JoinColumn(name = "app_process_id"))
  private Set<InfoMapping> license = new HashSet<>();

  @ElementCollection
  @CollectionTable(
      name = "app_process_cloud_info",
      joinColumns = @JoinColumn(name = "app_process_id"))
  private Set<InfoMapping> cloudInfos = new HashSet<>();

  @ElementCollection
  @CollectionTable(
      name = "app_process_client_info",
      joinColumns = @JoinColumn(name = "app_process_id"))
  private Set<InfoMapping> clientInfos = new HashSet<>();

  @ElementCollection
  @CollectionTable(
      name = "app_process_origin_info",
      joinColumns = @JoinColumn(name = "app_process_id"))
  private Set<InfoMapping> originInfos = new HashSet<>();
}
