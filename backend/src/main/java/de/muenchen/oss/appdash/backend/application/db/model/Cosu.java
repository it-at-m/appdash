package de.muenchen.oss.appdash.backend.application.db.model;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

import de.muenchen.oss.appdash.backend.Constants;
import de.muenchen.oss.appdash.backend.common.BaseLongEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity
@Table(name = "cosu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Cosu extends BaseLongEntity {
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

  @Column(name = "description", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String description;

  @Column(name = "info_url", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String infoUrl;

  @Column(name = "mdm", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String mdm;

  @Column(name = "e_key", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String eKey;

  @Column(name = "epic_key", columnDefinition = Constants.COLUMN_TYPE_TEXT)
  private String epicKey;

  @ManyToOne
  @JoinColumn(name = "number_of_users_id")
  @Audited(targetAuditMode = NOT_AUDITED)
  private TypeValue numberOfUsers;

  @ManyToOne
  @JoinColumn(name = "priority_id")
  @Audited(targetAuditMode = NOT_AUDITED)
  private TypeValue priority;

  @ManyToOne
  @JoinColumn(name = "os_id")
  @Audited(targetAuditMode = NOT_AUDITED)
  private TypeValue os;

  @ManyToOne
  @JoinColumn(name = "status_id")
  @Audited(targetAuditMode = NOT_AUDITED)
  private TypeValue status;

  @ManyToOne
  @JoinColumn(name = "lane_id")
  @Audited(targetAuditMode = NOT_AUDITED)
  private TypeValue lane;

  @ElementCollection
  @CollectionTable(name = "cosu_comment_info", joinColumns = @JoinColumn(name = "cosu_id"))
  private Set<InfoMapping> commentInfos = new HashSet<>();

  @ElementCollection
  @CollectionTable(name = "cosu_url_info", joinColumns = @JoinColumn(name = "cosu_id"))
  private Set<InfoMapping> urlInfos = new HashSet<>();

  @ElementCollection
  @CollectionTable(name = "cosu_client_info", joinColumns = @JoinColumn(name = "cosu_id"))
  private Set<InfoMapping> clientInfos = new HashSet<>();

  @ElementCollection
  @CollectionTable(name = "cosu_origin_info", joinColumns = @JoinColumn(name = "cosu_id"))
  private Set<InfoMapping> originInfos = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "cosu_assignment",
      joinColumns = @JoinColumn(name = "cosu_id"),
      inverseJoinColumns = @JoinColumn(name = "app_id"))
  private Set<App> apps = new HashSet<>();
}
