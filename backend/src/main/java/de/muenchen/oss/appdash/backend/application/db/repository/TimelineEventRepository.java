package de.muenchen.oss.appdash.backend.application.db.repository;

import de.muenchen.oss.appdash.backend.application.db.model.TimelineEvent;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimelineEventRepository extends JpaRepository<TimelineEvent, Long> {
  @EntityGraph(attributePaths = {"status"})
  List<TimelineEvent> findByAppProcessIdOrderByTimestampStartAsc(Long appProcessId);
}
