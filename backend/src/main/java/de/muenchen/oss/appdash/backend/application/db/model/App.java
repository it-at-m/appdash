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
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "app")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class App extends BaseLongEntity {
  private static final long serialVersionUID = 1L;

  @CreationTimestamp
  @Column(name = "timestamp_created")
  private Instant timestampCreated;

  @UpdateTimestamp
  @Column(name = "timestamp_updated")
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

  @ManyToOne
  @JoinColumn(name = "priority_id")
  private LookupValue priority;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private LookupValue category;

  @ManyToOne
  @JoinColumn(name = "mbuc_id")
  private LookupValue mbuc;

  @ManyToOne
  @JoinColumn(name = "source_id")
  private LookupValue source;

  @ManyToOne
  @JoinColumn(name = "visibility_id")
  private LookupValue visibility;

  @ElementCollection
  @CollectionTable(name = "app_customer_info", joinColumns = @JoinColumn(name = "app_id"))
  private Set<InfoMapping> customerInfos = new HashSet<>();

  @ElementCollection
  @CollectionTable(name = "app_url_info", joinColumns = @JoinColumn(name = "app_id"))
  private Set<InfoMapping> urlInfos = new HashSet<>();
}
