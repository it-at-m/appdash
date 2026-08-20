package de.muenchen.oss.appdash.backend.application.db.repository;

import de.muenchen.oss.appdash.backend.application.db.model.LookupValue;
import de.muenchen.oss.appdash.backend.configuration.application.CacheConfiguration;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LookupValueRepository extends JpaRepository<LookupValue, Long> {
  @Override
  @NonNull @Cacheable(CacheConfiguration.REFERENCE_DATA)
  Optional<LookupValue> findById(@NonNull Long id);

  @Cacheable(CacheConfiguration.REFERENCE_DATA)
  List<LookupValue> findByType(@NonNull String type);

  @Cacheable(CacheConfiguration.REFERENCE_DATA)
  Optional<LookupValue> findByTypeAndName(@NonNull String type, @NonNull String name);

  @Override
  @NonNull @CacheEvict(value = CacheConfiguration.REFERENCE_DATA, allEntries = true)
  <S extends LookupValue> S save(@NonNull S entity);

  @Override
  @CacheEvict(value = CacheConfiguration.REFERENCE_DATA, allEntries = true)
  void deleteById(@NonNull Long id);
}
