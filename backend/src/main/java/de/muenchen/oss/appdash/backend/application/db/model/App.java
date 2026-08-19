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
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "app")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class App extends BaseLongEntity {
  private static final long serialVersionUID = 1L;

  @Column(name = "img", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String img;

  @Column(name = "mdm", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String mdm;

  @Column(name = "name", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String name;

  @Column(name = "creator", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String creator;

  @CreationTimestamp
  @Column(name = "timestamp_created")
  private Instant timestampCreated;

  @UpdateTimestamp
  @Column(name = "timestamp_updated")
  private Instant timestampUpdated;

  @Column(name = "date_added")
  private Instant dateAdded;

  @Column(name = "last_updated")
  private Instant lastUpdated;

  @Column(name = "last_status_change")
  private Instant lastStatusChange;

  @Column(name = "last_app_update")
  private Instant lastAppUpdate;

  @Column(name = "date_tracking")
  private Instant dateTracking;

  @Column(name = "last_app_control")
  private Instant lastAppControl;

  @Column(name = "description", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String description;

  @Column(name = "info_url", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String infoUrl;

  @Column(name = "info_customer", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String infoCustomer;

  @Column(name = "trend")
  private Integer trend;

  @Column(name = "number_of_users", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String numberOfUsers;

  @Column(name = "bundle_id", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String bundleId;

  @Column(name = "appstore_id", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String appstoreId;

  @Column(name = "e_key", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String eKey;

  @Column(name = "epic_key", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String epicKey;

  @Column(name = "rsm_key", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String rsmKey;

  @Column(name = "is_pilot")
  private Boolean isPilot;

  @Column(name = "is_critical")
  private Boolean isCritical;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  @ManyToOne
  @JoinColumn(name = "software_type_id")
  private SoftwareType softwareType;

  @ManyToOne
  @JoinColumn(name = "software_nature_id")
  private SoftwareNature softwareNature;

  @ManyToOne
  @JoinColumn(name = "priority_id")
  private Priority priority;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "referat_id")
  private Referat referat;

  @ManyToOne
  @JoinColumn(name = "mbuc_id")
  private Mbuc mbuc;

  @ManyToOne
  @JoinColumn(name = "os_id")
  private OS os;

  @ManyToOne
  @JoinColumn(name = "status_id")
  private Status status;

  @ManyToOne
  @JoinColumn(name = "lane_id")
  private Lane lane;

  @ManyToOne
  @JoinColumn(name = "source_id")
  private Source source;

  @ManyToOne
  @JoinColumn(name = "visibility_id")
  private Visibility visibility;
}
