package de.muenchen.oss.appdash.backend.application.db.repository;

import de.muenchen.oss.appdash.backend.application.db.model.App;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository
    extends JpaRepository<App, Long>, RevisionRepository<App, Long, Integer> {
  @EntityGraph(
      attributePaths = {
        "appProcesses",
        "appProcesses.status",
        "appProcesses.os",
        "appProcesses.lane"
      })
  List<App> findAllWithAppProcessesBy();

  @EntityGraph(
      attributePaths = {
        "appProcesses",
        "appProcesses.status",
        "appProcesses.os",
        "appProcesses.lane"
      })
  Optional<App> findWithAppProcessesById(Long id);
}
