package de.muenchen.oss.appdash.backend.application.db.repository;

import de.muenchen.oss.appdash.backend.application.db.model.Client;
import de.muenchen.oss.appdash.backend.configuration.application.CacheConfiguration;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
  @Override
  @NonNull @Cacheable(CacheConfiguration.REFERENCE_DATA)
  Optional<Client> findById(@NonNull Long id);

  @Cacheable(CacheConfiguration.REFERENCE_DATA)
  Optional<Client> findByName(@NonNull String name);

  @Override
  @NonNull @CacheEvict(value = CacheConfiguration.REFERENCE_DATA, allEntries = true)
  <S extends Client> S save(@NonNull S entity);

  @Override
  @CacheEvict(value = CacheConfiguration.REFERENCE_DATA, allEntries = true)
  void deleteById(@NonNull Long id);
}
