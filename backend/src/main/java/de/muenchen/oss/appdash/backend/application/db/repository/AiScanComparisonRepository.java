package de.muenchen.oss.appdash.backend.application.db.repository;

import de.muenchen.oss.appdash.backend.application.db.model.AiScanComparison;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiScanComparisonRepository extends JpaRepository<AiScanComparison, Long> {
  List<AiScanComparison> findByScanOneIdOrScanTwoId(Long scanOneId, Long scanTwoId);
}
