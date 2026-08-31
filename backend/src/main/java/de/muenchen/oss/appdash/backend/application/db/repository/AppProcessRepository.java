package de.muenchen.oss.appdash.backend.application.db.repository;

import de.muenchen.oss.appdash.backend.application.db.model.AppProcess;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppProcessRepository
    extends JpaRepository<AppProcess, Long>, RevisionRepository<AppProcess, Long, Integer> {
  List<AppProcess> findByAppId(Long appId);
}
