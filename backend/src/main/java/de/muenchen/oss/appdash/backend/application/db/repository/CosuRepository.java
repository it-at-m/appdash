package de.muenchen.oss.appdash.backend.application.db.repository;

import de.muenchen.oss.appdash.backend.application.db.model.Cosu;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CosuRepository
    extends JpaRepository<Cosu, Long>, RevisionRepository<Cosu, Long, Integer> {
  Optional<Cosu> findByName(String name);

  List<Cosu> findByAppsId(Long appId);
}
