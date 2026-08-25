package de.muenchen.oss.appdash.backend.application.db.model;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class RevisionUserListener implements RevisionListener {
  @Override
  public void newRevision(final Object revisionEntity) {
    final RevisionUser rev = (RevisionUser) revisionEntity;
    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated()) {
      rev.setUsername(auth.getName());
    } else {
      rev.setUsername("system");
    }
  }
}
