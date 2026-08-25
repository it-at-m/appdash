package de.muenchen.oss.appdash.backend.application.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Entity
@Table(name = "revision_user")
@RevisionEntity(RevisionUserListener.class)
@Getter
@Setter
public class RevisionUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @RevisionNumber
  @Column(name = "rev")
  private int id;

  @RevisionTimestamp
  @Column(name = "revtstmp")
  private long timestamp;

  @Column(name = "username")
  private String username;
}
