package de.muenchen.oss.appdash.backend.application.db.repository;

import de.muenchen.oss.appdash.backend.application.db.model.AiScanReport;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiScanReportRepository extends JpaRepository<AiScanReport, Long> {
  Optional<AiScanReport> findByScanId(Long scanId);
}
