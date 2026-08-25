package de.muenchen.oss.appdash.backend.application.db.repository;

import de.muenchen.oss.appdash.backend.application.db.model.TypeEnum;
import de.muenchen.oss.appdash.backend.application.db.model.TypeValue;
import de.muenchen.oss.appdash.backend.configuration.application.CacheConfiguration;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeValueRepository extends JpaRepository<TypeValue, Long> {
  @Override
  @NonNull @Cacheable(CacheConfiguration.REFERENCE_DATA)
  Optional<TypeValue> findById(@NonNull Long id);

  @Cacheable(CacheConfiguration.REFERENCE_DATA)
  List<TypeValue> findByType(@NonNull TypeEnum type);

  @Cacheable(CacheConfiguration.REFERENCE_DATA)
  Optional<TypeValue> findByTypeAndName(@NonNull TypeEnum type, @NonNull String name);

  @Override
  @NonNull @CacheEvict(value = CacheConfiguration.REFERENCE_DATA, allEntries = true)
  <S extends TypeValue> S save(@NonNull S entity);

  @Override
  @CacheEvict(value = CacheConfiguration.REFERENCE_DATA, allEntries = true)
  void deleteById(@NonNull Long id);
}
