package de.muenchen.oss.appdash.backend.application.db.repository;

import de.muenchen.oss.appdash.backend.application.db.model.Scan;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanRepository extends JpaRepository<Scan, Long> {
  @EntityGraph(attributePaths = {"fileType", "provider"})
  List<Scan> findByAppProcessId(Long appProcessId);

  @EntityGraph(attributePaths = {"fileType", "provider"})
  Optional<Scan> findFirstByAppProcessIdOrderByTimestampCreatedDesc(Long appProcessId);
}
