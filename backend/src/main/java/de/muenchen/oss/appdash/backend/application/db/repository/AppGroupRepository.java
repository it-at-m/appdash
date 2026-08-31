package de.muenchen.oss.appdash.backend.application.db.repository;

import de.muenchen.oss.appdash.backend.application.db.model.AppGroup;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppGroupRepository
    extends JpaRepository<AppGroup, Long>, RevisionRepository<AppGroup, Long, Integer> {
  Optional<AppGroup> findByMail(String mail);

  List<AppGroup> findByAppsId(Long appId);
}
