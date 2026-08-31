package de.muenchen.oss.appdash.backend.application.db.model;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

import de.muenchen.oss.appdash.backend.Constants;
import de.muenchen.oss.appdash.backend.common.BaseLongEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity
@Table(name = "app")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class App extends BaseLongEntity {
  private static final long serialVersionUID = 1L;

  @CreationTimestamp
  @Column(name = "timestamp_created")
  @NotAudited
  private Instant timestampCreated;

  @UpdateTimestamp
  @Column(name = "timestamp_updated")
  @NotAudited
  private Instant timestampUpdated;

  @Column(name = "name", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String name;

  @Column(name = "creator", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String creator;

  @Column(name = "img", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String img;

  @Column(name = "description", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String description;

  @Column(name = "bundle_id", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String bundleId;

  @Column(name = "appstore_id", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String appstoreId;

  @Column(name = "e_key", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String eKey;

  @Column(name = "epic_key", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String epicKey;

  @Column(name = "privacy_policy", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String privacyPolicy;

  @Column(name = "website", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String website;

  @Column(name = "is_internal")
  private Boolean isInternal;

  @ManyToOne
  @JoinColumn(name = "priority_id")
  @Audited(targetAuditMode = NOT_AUDITED)
  private TypeValue priority;

  @ManyToOne
  @JoinColumn(name = "category_id")
  @Audited(targetAuditMode = NOT_AUDITED)
  private TypeValue category;

  @ManyToOne
  @JoinColumn(name = "mbuc_id")
  @Audited(targetAuditMode = NOT_AUDITED)
  private TypeValue mbuc;

  @ManyToOne
  @JoinColumn(name = "visibility_id")
  @Audited(targetAuditMode = NOT_AUDITED)
  private TypeValue visibility;

  @ElementCollection
  @CollectionTable(name = "app_customer_info", joinColumns = @JoinColumn(name = "app_id"))
  private Set<InfoMapping> customerInfos = new HashSet<>();

  @ElementCollection
  @CollectionTable(name = "app_url_info", joinColumns = @JoinColumn(name = "app_id"))
  private Set<InfoMapping> urlInfos = new HashSet<>();

  @ElementCollection
  @CollectionTable(name = "app_img_info", joinColumns = @JoinColumn(name = "app_id"))
  private Set<InfoMapping> imgInfos = new HashSet<>();

  @ElementCollection
  @CollectionTable(name = "app_privacy_info", joinColumns = @JoinColumn(name = "app_id"))
  private Set<InfoMapping> privacyInfos = new HashSet<>();

  @ManyToMany(mappedBy = "apps")
  private Set<Cosu> cosus = new HashSet<>();

  @ManyToMany(mappedBy = "apps")
  private Set<AppGroup> appGroups = new HashSet<>();

  @OneToMany(mappedBy = "app", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<AppProcess> appProcesses = new HashSet<>();
}
