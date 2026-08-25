package de.muenchen.oss.appdash.backend.application.db.model;

import de.muenchen.oss.appdash.backend.common.BaseLongEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "app_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class AppGroup extends BaseLongEntity {
  private static final long serialVersionUID = 1L;

  @CreationTimestamp
  @Column(name = "timestamp_created")
  @NotAudited
  private Instant timestampCreated;

  @UpdateTimestamp
  @Column(name = "timestamp_updated")
  @NotAudited
  private Instant timestampUpdated;

  @Column(name = "mail", length = 254, nullable = false)
  private String mail;

  @Column(name = "color", length = 7, nullable = false)
  private String color = "#0088ff";

  @Column(name = "setting_control_interval", nullable = false)
  private Integer settingControlInterval = 3;

  @Column(name = "setting_mail_report", nullable = false)
  private Boolean settingMailReport = false;

  @Column(name = "setting_mail_scan_error", nullable = false)
  private Boolean settingMailScanError = false;

  @Column(name = "setting_mail_scan_degraded", nullable = false)
  private Boolean settingMailScanDegraded = false;

  @Column(name = "setting_mail_scan_success", nullable = false)
  private Boolean settingMailScanSuccess = false;

  @Column(name = "setting_mail_app_removed", nullable = false)
  private Boolean settingMailAppRemoved = false;

  @ManyToMany
  @JoinTable(
      name = "app_group_assignment",
      joinColumns = @JoinColumn(name = "app_group_id"),
      inverseJoinColumns = @JoinColumn(name = "app_id"))
  private Set<App> apps = new HashSet<>();
}
